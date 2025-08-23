package com.teemlaren.RTFD.service;



import com.teemlaren.RTFD.entity.FraudDecision;
import com.teemlaren.RTFD.Fraud.FraudRuleEngine;
import com.teemlaren.RTFD.entity.Transaction;
import com.teemlaren.RTFD.repository.FraudDecisionRepository;
import com.teemlaren.RTFD.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.teemlaren.RTFD.entity.FraudDecision.Decision.APPROVED;

@Service
public class TransactionService {

    private final TransactionRepository repo;
    private final FraudRuleEngine ruleEngine;
    private final FraudDecisionRepository fraudDecisionRepo;

    public TransactionService(TransactionRepository repo, FraudRuleEngine ruleEngine, FraudDecisionRepository fraudDecisionRepo) {
        this.repo = repo;
        this.ruleEngine = ruleEngine;
        this.fraudDecisionRepo=fraudDecisionRepo;
    }

    @Transactional
    public Transaction createAndDecide(Transaction tx) {
        Transaction savedTx =repo.save(tx);
        // initial save (optional) if you want an ID first; or evaluate first then save
        FraudDecision decision = ruleEngine.evaluate(savedTx);

        Transaction.Status mappedStatus=switch (decision.getDecision()){
            case APPROVED -> Transaction.Status.APPROVED;
            case DECLINED -> Transaction.Status.DECLINED;
            case CHALLENGE -> Transaction.Status.CHALLENGE;
        };
        savedTx.setStatus(mappedStatus);
        savedTx.setDecisionReason(String.join(",",
                decision.getReasons().stream().map(Enum::name).toList()
        ));
        savedTx.setScore(decision.getScore());

        // 4. Link decision with transactionId and save
        decision.setTransactionId(savedTx.getId());
        fraudDecisionRepo.save(decision);

        return repo.save(tx);
    }
}

