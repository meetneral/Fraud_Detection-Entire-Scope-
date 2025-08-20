package com.teemlaren.RTFD.entity;


import com.teemlaren.RTFD.entity.Transaction;
import com.teemlaren.RTFD.enums.DecisionReason;

import java.util.ArrayList;
import java.util.List;

public class FraudDecision {
    private Transaction.Status status;
    private final List<DecisionReason> reasons = new ArrayList<>();
    private Double score; // optional ML score

    public FraudDecision(Transaction.Status status) {
        this.status = status;
    }

    public Transaction.Status getStatus() {
        return status;
    }

    public void setStatus(Transaction.Status status) {
        this.status = status;
    }

    public List<DecisionReason> getReasons() {
        return reasons;
    }

    public void addReason(DecisionReason r) {
        reasons.add(r);
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}

