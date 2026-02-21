package com.blackrock.challenge.investmentengine.domain;


import java.math.BigDecimal;

public record TransactionTotals(
        BigDecimal amount,
        BigDecimal ceiling,
        BigDecimal remanent
) {}

