package com.teemlaren.RTFD.dto;

import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long id,
        String merchantId,
        Double amount,
        String currency,
        String country,
        String deviceId,
        String cardHash,
        LocalDateTime createdAt,
        String decisionReason,
        Double score,
        String status
) {}

