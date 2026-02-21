package com.blackrock.challenge.investmentengine.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record QPeriod(
        BigDecimal fixed,
        LocalDateTime start,
        LocalDateTime end
) {}

