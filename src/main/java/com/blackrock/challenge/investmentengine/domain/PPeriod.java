package com.blackrock.challenge.investmentengine.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PPeriod(
        BigDecimal extra,
        LocalDateTime start,
        LocalDateTime end
) {}

