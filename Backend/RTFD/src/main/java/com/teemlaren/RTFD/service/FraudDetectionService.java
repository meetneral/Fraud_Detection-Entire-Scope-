package com.teemlaren.RTFD.service;

import com.teemlaren.RTFD.entity.Transaction;
import org.springframework.stereotype.Service;

@Service
public class FraudDetectionService {

    public Transaction evaluate(Transaction tx) {
        // Rule 1: High amount transactions
        if (tx.getAmount() != null && tx.getAmount() > 100000) {
            tx.setStatus(Transaction.Status.DECLINED);
            tx.setDecisionReason("Amount exceeds ₹1,00,000");
            tx.setScore(0.9); // high fraud probability
            return tx;
        }

        // Rule 2: Suspicious country
        if ("NG".equalsIgnoreCase(tx.getCountry())) {
            tx.setStatus(Transaction.Status.CHALLENGE);
            tx.setDecisionReason("Suspicious country detected");
            tx.setScore(0.7);
            return tx;
        }

        // Rule 3: Suspicious device
        if ("D999".equalsIgnoreCase(tx.getDeviceId())) {
            tx.setStatus(Transaction.Status.DECLINED);
            tx.setDecisionReason("Blacklisted device");
            tx.setScore(0.95);
            return tx;
        }

        // Otherwise safe → approve
        tx.setStatus(Transaction.Status.APPROVED);
        tx.setDecisionReason("Transaction looks normal");
        tx.setScore(0.1);
        return tx;
    }
}
