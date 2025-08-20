package com.teemlaren.RTFD.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

@Validated
@ConfigurationProperties(prefix = "fraud.rules")
public class FraudRulesProperties {

    /** Decline if amount > amountMaxDecline */
    private double amountMaxDecline = 100000.0;

    /** Challenge if amount > amountMaxChallenge (but <= decline) */
    private double amountMaxChallenge = 50000.0;

    private Set<String> blockedCountries = new HashSet<>();
    private Set<String> blockedDevices = new HashSet<>();
    private Set<String> blockedCards = new HashSet<>();

    public double getAmountMaxDecline() { return amountMaxDecline; }
    public void setAmountMaxDecline(double v) { this.amountMaxDecline = v; }

    public double getAmountMaxChallenge() { return amountMaxChallenge; }
    public void setAmountMaxChallenge(double v) { this.amountMaxChallenge = v; }

    public Set<String> getBlockedCountries() { return blockedCountries; }
    public void setBlockedCountries(Set<String> s) { this.blockedCountries = s; }

    public Set<String> getBlockedDevices() { return blockedDevices; }
    public void setBlockedDevices(Set<String> s) { this.blockedDevices = s; }

    public Set<String> getBlockedCards() { return blockedCards; }
    public void setBlockedCards(Set<String> s) { this.blockedCards = s; }
}
