package com.blackrock.challenge.investmentengine.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TransactionDto {

    private String date;
    private double amount;
    private double ceiling;
    private double remanent;
}

