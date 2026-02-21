package com.blackrock.challenge.investmentengine.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Transaction {

    private LocalDateTime date;
    private BigDecimal amount;
    private BigDecimal ceiling;
    private BigDecimal remanent;
}

