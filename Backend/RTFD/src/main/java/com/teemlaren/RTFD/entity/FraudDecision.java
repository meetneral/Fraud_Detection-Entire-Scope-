package com.teemlaren.RTFD.entity;

import com.teemlaren.RTFD.enums.DecisionReason;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fraud_decisions")
public class FraudDecision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;


    private String ruleTriggered; // e.g., AMOUNT_LIMIT, COUNTRY_BLACKLIST, NEW_DEVICE

    @Enumerated(EnumType.STRING)
    private Decision decision; // APPROVED, DECLINED, CHALLENGE

    private Instant timestamp = Instant.now();

    private Double score; // optional ML score

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "fraud_decision_reasons", joinColumns = @JoinColumn(name = "fraud_decision_id"))
    @Column(name = "reason")
    private List<DecisionReason> reasons = new ArrayList<>();

    // ===== ENUM =====
    public enum Decision {
        APPROVED, DECLINED, CHALLENGE
    }

    // ===== CONSTRUCTORS =====
    public FraudDecision() {
        // Required by JPA
    }

    public FraudDecision(Transaction transaction, String ruleTriggered, Decision decision, Double score) {
        this.transaction = transaction;
        this.ruleTriggered = ruleTriggered;
        this.decision = decision;
        this.score = score;
        this.timestamp = Instant.now();
    }

    // ===== GETTERS + SETTERS =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    public String getRuleTriggered() {
        return ruleTriggered;
    }

    public void setRuleTriggered(String ruleTriggered) {
        this.ruleTriggered = ruleTriggered;
    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public List<DecisionReason> getReasons() {
        return reasons;
    }

    public void setReasons(List<DecisionReason> reasons) {
        this.reasons = reasons;
    }

    public void addReason(DecisionReason reason) {
        this.reasons.add(reason);
    }
}
