package com.teemlaren.RTFD.Fraud;


import com.teemlaren.RTFD.entity.FraudDecision;
import com.teemlaren.RTFD.entity.Transaction;
import com.teemlaren.RTFD.enums.DecisionReason;
import com.teemlaren.RTFD.ml.MlScoringClient;
import com.teemlaren.RTFD.prop.FraudRulesProperties;
import org.springframework.stereotype.Component;

@Component
public class FraudRuleEngine {

    private final FraudRulesProperties props;
    private final MlScoringClient mlClient;

    public FraudRuleEngine(FraudRulesProperties props,
                           MlScoringClient mlClient) {
        this.props = props;
        this.mlClient = mlClient;
    }

   /* public FraudDecision evaluate(Transaction tx) {
        FraudDecision decision = new FraudDecision(Transaction.Status.APPROVED);

        // normalize inputs
        String country = tx.getCountry() == null ? "" : tx.getCountry().trim().toUpperCase();
        String device = tx.getDeviceId() == null ? "" : tx.getDeviceId().trim().toLowerCase();
        String card = tx.getCardHash() == null ? "" : tx.getCardHash().trim().toLowerCase();

        // --- Rules ---
        // 1) Amount thresholds
        if (tx.getAmount() != null) {
            double amt = tx.getAmount();
            if (amt > props.getAmountMaxDecline()) {
                decision.setStatus(Transaction.Status.DECLINED);
                decision.addReason(DecisionReason.AMOUNT_LIMIT);
            } else if (amt > props.getAmountMaxChallenge()
                    && decision.getStatus() != Transaction.Status.DECLINED) {
                decision.setStatus(Transaction.Status.CHALLENGE);
                decision.addReason(DecisionReason.AMOUNT_LIMIT);
            }
        }

        // 2) Block lists
        if (props.getBlockedCountries().contains(country)) {
            decision.setStatus(Transaction.Status.DECLINED);
            decision.addReason(DecisionReason.COUNTRY_BLOCKED);
        }
        if (props.getBlockedDevices().contains(device)) {
            decision.setStatus(Transaction.Status.DECLINED);
            decision.addReason(DecisionReason.DEVICE_BLOCKED);
        }
        if (props.getBlockedCards().contains(card)) {
            decision.setStatus(Transaction.Status.DECLINED);
            decision.addReason(DecisionReason.CARD_BLOCKED);
        }

        return decision;
    }*/


    public FraudDecision evaluate(Transaction tx) {
        FraudDecision decision = new FraudDecision(  tx.getId(),
                "ML_RULE_ENGINE",   // ruleTriggered
                FraudDecision.Decision.APPROVED,
                null      );


        // Build features for ML
        var features = java.util.Map.<String,Object>of(
                "amount", tx.getAmount(),
                "country", tx.getCountry(),
                "currency", tx.getCurrency(),
                "deviceId", tx.getDeviceId(),
                "merchantId", tx.getMerchantId()
        );

        Double score = mlClient.score(features);
        if (score != null) {
            decision.setScore(score);
            if (score >= mlClient.getDeclineThreshold()) {
                decision.setDecision(FraudDecision.Decision.DECLINED);
                decision.addReason(DecisionReason.ML_SCORE_HIGH);
            } else if (score >= mlClient.getChallengeThreshold()
                    && decision.getDecision() != FraudDecision.Decision.DECLINED) {
                decision.setDecision(FraudDecision.Decision.CHALLENGE);
                decision.addReason(DecisionReason.ML_SCORE_MEDIUM);
            }
        }

        return decision;
    }
}
