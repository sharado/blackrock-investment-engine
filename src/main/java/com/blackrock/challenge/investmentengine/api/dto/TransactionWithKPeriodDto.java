package com.blackrock.challenge.investmentengine.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionWithKPeriodDto {

    private String date;
    private double amount;
    private double ceiling;
    private double remanent;
    private boolean inKPeriod;
}

