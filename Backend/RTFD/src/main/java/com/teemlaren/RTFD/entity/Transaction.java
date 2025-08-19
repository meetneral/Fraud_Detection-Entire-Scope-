package com.teemlaren.RTFD.entity;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchantId;
    private Double amount;
    private String currency;
    private String country;
    private String deviceId;
    private String cardHash;
    private Instant createdAt = Instant.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public enum Status {
        PENDING, APPROVED, DECLINED, CHALLENGE
    }

    // ---- Constructors ----
    public Transaction() {
    }

    public Transaction(String merchantId, Double amount, String currency, String country,
                       String deviceId, String cardHash, Instant createdAt, Status status) {
        this.merchantId = merchantId;
        this.amount = amount;
        this.currency = currency;
        this.country = country;
        this.deviceId = deviceId;
        this.cardHash = cardHash;
        this.createdAt = createdAt;
        this.status = status;
    }

    // ---- Getters & Setters ----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCardHash() {
        return cardHash;
    }

    public void setCardHash(String cardHash) {
        this.cardHash = cardHash;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // ---- toString ----
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", merchantId='" + merchantId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", country='" + country + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", cardHash='" + cardHash + '\'' +
                ", createdAt=" + createdAt +
                ", status=" + status +
                '}';
    }
}

