package com.blackrock.challenge.investmentengine.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record TransactionValidationRequestDto(
        double wage,
        List<TransactionDto> transactions
) {}
