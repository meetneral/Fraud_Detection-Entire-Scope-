package com.teemlaren.RTFD.service;

import com.teemlaren.RTFD.entity.Transaction;
import com.teemlaren.RTFD.entity.FraudDecision;
import com.teemlaren.RTFD.enums.DecisionReason;
import com.teemlaren.RTFD.repository.FraudDecisionRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class FraudDetectionService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final FraudDecisionRepository fraudDecisionRepository;


    public FraudDetectionService(FraudDecisionRepository fraudDecisionRepository) {
        this.fraudDecisionRepository = fraudDecisionRepository;
    }

    public Transaction evaluate(Transaction tx) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Transaction> request = new HttpEntity<>(tx, headers);

            // 🔹 Call Python scorer
            Map<String, Object> response = restTemplate.postForObject(
                    "http://localhost:5001/score",
                    request,
                    Map.class
            );

            if (response != null) {
                Object decision = response.get("decision");
                Object score = response.get("score");

                if (decision != null) {
                    tx.setStatus(Transaction.Status.valueOf(decision.toString()));
                }
                if (score != null) {
                    tx.setScore(Double.valueOf(score.toString()));
                }

                // ✅ Log decision
                logDecision(tx.getId(),
                        "PYTHON_SCORER",
                        FraudDecision.Decision.valueOf(tx.getStatus().name()));
            }

        } catch (Exception e) {
            // Rule 1: Amount limit
            if (tx.getAmount() != null && tx.getAmount() > 100000) {
                tx.setStatus(Transaction.Status.DECLINED);
                tx.setDecisionReason("Amount exceeds ₹1,00,000 " + DecisionReason.AMOUNT_LIMIT);
                tx.setScore(0.9);

                logDecision(tx.getId(),
                        "AMOUNT_LIMIT",
                        FraudDecision.Decision.DECLINED);
                return tx;
            }

            // Rule 2: Suspicious country
            if ("NG".equalsIgnoreCase(tx.getCountry())) {
                tx.setStatus(Transaction.Status.CHALLENGE);
                tx.setDecisionReason("Suspicious country detected " + DecisionReason.COUNTRY_BLOCKED);
                tx.setScore(0.7);

                logDecision(tx.getId(),
                        "COUNTRY_BLOCKED",
                        FraudDecision.Decision.CHALLENGE);
                return tx;
            }

            // Rule 3: Suspicious device
            if ("D999".equalsIgnoreCase(tx.getDeviceId())) {
                tx.setStatus(Transaction.Status.DECLINED);
                tx.setDecisionReason("Blacklisted device " + DecisionReason.DEVICE_BLOCKED);
                tx.setScore(0.95);

                logDecision(tx.getId(),
                        "DEVICE_BLOCKED",
                        FraudDecision.Decision.DECLINED);
                return tx;
            }

            // Otherwise safe → approve
            tx.setStatus(Transaction.Status.APPROVED);
            tx.setDecisionReason("Transaction looks normal");
            tx.setScore(0.1);

            logDecision(tx.getId(),
                    "NO_RULE",
                    FraudDecision.Decision.APPROVED);
            return tx;
        }
        return tx;
    }

    private void logDecision(Long transactionId, String rule, FraudDecision.Decision decision) {
        FraudDecision fd = new FraudDecision();
        fd.setTransactionId(transactionId);
        fd.setRuleTriggered(rule);
        fd.setDecision(decision);
        fraudDecisionRepository.save(fd);
    }
}
