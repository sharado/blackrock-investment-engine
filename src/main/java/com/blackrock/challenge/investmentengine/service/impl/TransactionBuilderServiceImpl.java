package com.blackrock.challenge.investmentengine.service.impl;

import com.blackrock.challenge.investmentengine.domain.*;
import com.blackrock.challenge.investmentengine.service.TransactionBuilderService;
import com.blackrock.challenge.investmentengine.util.DecimalUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TransactionBuilderServiceImpl implements TransactionBuilderService {

    @Override
    public List<Transaction> buildTransactions(List<Expense> expenses) {
        return expenses.stream()
                .map(this::buildTransaction)
                .toList();
    }

    private Transaction buildTransaction(Expense expense) {

        BigDecimal amount = expense.amount();

        BigDecimal ceiling = amount
                .divide(DecimalUtils.HUNDRED, 0, RoundingMode.CEILING)
                .multiply(DecimalUtils.HUNDRED);

        BigDecimal remanent = ceiling.subtract(amount);

        return Transaction.builder()
                .date(expense.date())
                .amount(amount)
                .ceiling(ceiling)
                .remanent(remanent)
                .build();
    }

    @Override
    public TransactionTotals calculateTotals(List<Transaction> transactions) {

        BigDecimal totalAmount = DecimalUtils.ZERO;
        BigDecimal totalCeiling = DecimalUtils.ZERO;
        BigDecimal totalRemanent = DecimalUtils.ZERO;

        for (Transaction tx : transactions) {
            totalAmount = totalAmount.add(tx.getAmount());
            totalCeiling = totalCeiling.add(tx.getCeiling());
            totalRemanent = totalRemanent.add(tx.getRemanent());
        }

        return new TransactionTotals(
                totalAmount,
                totalCeiling,
                totalRemanent
        );
    }
}
