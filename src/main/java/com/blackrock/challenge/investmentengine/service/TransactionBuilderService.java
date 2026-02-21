package com.blackrock.challenge.investmentengine.service;


import com.blackrock.challenge.investmentengine.domain.Expense;
import com.blackrock.challenge.investmentengine.domain.Transaction;
import com.blackrock.challenge.investmentengine.domain.TransactionTotals;

import java.util.List;

public interface TransactionBuilderService {

    List<Transaction> buildTransactions(List<Expense> expenses);

    TransactionTotals calculateTotals(List<Transaction> transactions);
}

