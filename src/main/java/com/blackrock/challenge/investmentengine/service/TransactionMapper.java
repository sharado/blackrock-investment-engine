package com.blackrock.challenge.investmentengine.service;

import com.blackrock.challenge.investmentengine.api.dto.*;
import com.blackrock.challenge.investmentengine.domain.*;
import com.blackrock.challenge.investmentengine.util.DecimalUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class TransactionMapper {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TransactionMapper() {}

    public static List<Expense> toExpenses(List<ExpenseRequestDto> dtos) {
        return dtos.stream()
                .map(d -> new Expense(
                        LocalDateTime.parse(d.getDate(), FORMATTER),
                        DecimalUtils.bd(d.getAmount())
                ))
                .toList();
    }

    public static TransactionDto toDto(Transaction tx) {
        return TransactionDto.builder()
                .date(tx.getDate().format(FORMATTER))
                .amount(tx.getAmount().doubleValue())
                .ceiling(tx.getCeiling().doubleValue())
                .remanent(tx.getRemanent().doubleValue())
                .build();
    }

    public static TransactionWithKPeriodDto toDto(Transaction tx, boolean inKPeriod) {
        return TransactionWithKPeriodDto.builder()
                .date(tx.getDate().format(FORMATTER))
                .amount(tx.getAmount().doubleValue())
                .ceiling(tx.getCeiling().doubleValue())
                .remanent(tx.getRemanent().doubleValue())
                .inKPeriod(inKPeriod)
                .build();
    }

    public static TransactionTotalsDto toDto(TransactionTotals totals) {
        return TransactionTotalsDto.builder()
                .amount(totals.amount().doubleValue())
                .ceiling(totals.ceiling().doubleValue())
                .remanent(totals.remanent().doubleValue())
                .build();
    }

    public static Transaction toDomain(TransactionDto dto) {
        return Transaction.builder()
                .date(LocalDateTime.parse(dto.getDate(), FORMATTER))
                .amount(DecimalUtils.bd(dto.getAmount()))
                .ceiling(DecimalUtils.bd(dto.getCeiling()))
                .remanent(DecimalUtils.bd(dto.getRemanent()))
                .build();
    }

    public static InvalidTransactionDto toInvalidDto(
            Transaction tx,
            String message
    ) {
        return InvalidTransactionDto.builder()
                .date(tx.getDate().format(FORMATTER))
                .amount(tx.getAmount().doubleValue())
                .ceiling(tx.getCeiling().doubleValue())
                .remanent(tx.getRemanent().doubleValue())
                .message(message)
                .build();
    }

}
