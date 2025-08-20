package com.teemlaren.RTFD.ml;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ml.scorer")
public class MlScorerProperties {
    private String url = "http://localhost:9000/score";
    private double challengeThreshold = 0.60;
    private double declineThreshold = 0.85;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getChallengeThreshold() {
        return challengeThreshold;
    }

    public void setChallengeThreshold(double v) {
        this.challengeThreshold = v;
    }

    public double getDeclineThreshold() {
        return declineThreshold;
    }

    public void setDeclineThreshold(double v) {
        this.declineThreshold = v;
    }
}

