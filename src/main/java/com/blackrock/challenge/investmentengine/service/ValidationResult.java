package com.blackrock.challenge.investmentengine.service;


import com.blackrock.challenge.investmentengine.domain.Transaction;

import java.util.List;

public record ValidationResult(
        List<Transaction> valid,
        List<InvalidTransaction> invalid
) {}

