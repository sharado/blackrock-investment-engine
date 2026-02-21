package com.blackrock.challenge.investmentengine.domain;

import java.time.LocalDateTime;

public record KPeriod(
        LocalDateTime start,
        LocalDateTime end
) {}

