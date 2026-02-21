package com.blackrock.challenge.investmentengine.domain;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record Expense(
        LocalDateTime date,
        BigDecimal amount
) {}

