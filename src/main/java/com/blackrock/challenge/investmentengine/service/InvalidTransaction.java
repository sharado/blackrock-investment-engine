package com.blackrock.challenge.investmentengine.service;


import com.blackrock.challenge.investmentengine.domain.Transaction;

public record InvalidTransaction(
        Transaction transaction,
        String message
) {}

