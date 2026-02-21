package com.blackrock.challenge.investmentengine.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TransactionParseResponseDto {

    private List<TransactionDto> transactions;
    private TransactionTotalsDto totals;
}

