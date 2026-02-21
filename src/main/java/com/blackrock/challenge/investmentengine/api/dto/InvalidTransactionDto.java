package com.blackrock.challenge.investmentengine.api.dto;


import lombok.Builder;

@Builder
public record InvalidTransactionDto(
        String date,
        double amount,
        double ceiling,
        double remanent,
        String message
) {}

