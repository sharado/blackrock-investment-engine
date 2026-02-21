package com.blackrock.challenge.investmentengine.api.dto;


import lombok.Builder;
import java.util.List;

@Builder
public record TransactionValidationResponseDto(
        List<TransactionDto> valid,
        List<InvalidTransactionDto> invalid
) {}

