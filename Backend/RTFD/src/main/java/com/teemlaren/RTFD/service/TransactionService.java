package com.teemlaren.RTFD.service;



import com.teemlaren.RTFD.entity.FraudDecision;
import com.teemlaren.RTFD.Fraud.FraudRuleEngine;
import com.teemlaren.RTFD.entity.Transaction;
import com.teemlaren.RTFD.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final TransactionRepository repo;
    private final FraudRuleEngine ruleEngine;

    public TransactionService(TransactionRepository repo, FraudRuleEngine ruleEngine) {
        this.repo = repo;
        this.ruleEngine = ruleEngine;
    }

    @Transactional
    public Transaction createAndDecide(Transaction tx) {
        // initial save (optional) if you want an ID first; or evaluate first then save
        FraudDecision decision = ruleEngine.evaluate(tx);
        tx.setStatus(decision.getStatus());
        tx.setDecisionReason(String.join(",",
                decision.getReasons().stream().map(Enum::name).toList()
        ));
        tx.setScore(decision.getScore());

        return repo.save(tx);
    }
}

