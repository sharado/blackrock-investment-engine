package com.blackrock.challenge.investmentengine.service;

import com.blackrock.challenge.investmentengine.domain.Transaction;

import java.util.List;

public interface TransactionValidatorService {

    ValidationResult validate(List<Transaction> transactions);
}

