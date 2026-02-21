package com.blackrock.challenge.investmentengine.service;

import com.blackrock.challenge.investmentengine.domain.Expense;
import com.blackrock.challenge.investmentengine.domain.Transaction;
import com.blackrock.challenge.investmentengine.domain.TransactionTotals;
import com.blackrock.challenge.investmentengine.service.impl.TransactionBuilderServiceImpl;
import com.blackrock.challenge.investmentengine.util.DecimalUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionBuilderServiceTest {

    private TransactionBuilderService service;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    void setUp() {
        service = new TransactionBuilderServiceImpl();
    }

    private Expense expense(String date, double amount) {
        return Expense.builder()
                .date(LocalDateTime.parse(date, FORMATTER))
                .amount(DecimalUtils.bd(amount))
                .build();
    }

    private Transaction transaction(
            String date,
            String amount,
            String ceiling,
            String remanent
    ) {
        return Transaction.builder()
                .date(LocalDateTime.parse(date, FORMATTER))
                .amount(DecimalUtils.bd(amount))
                .ceiling(DecimalUtils.bd(ceiling))
                .remanent(DecimalUtils.bd(remanent))
                .build();
    }

    @Test
    void shouldBuildTransactionsWithCorrectCeilingAndRemanent() {

        List<Expense> expenses = List.of(
                expense("2023-10-12 20:15:30", 250.00),
                expense("2023-02-28 15:49:20", 375.00),
                expense("2023-07-01 21:59:00", 620.00)
        );

        List<Transaction> transactions =
                service.buildTransactions(expenses);

        assertThat(transactions).hasSize(3);

        Transaction tx = transactions.get(0);

        assertThat(tx.getAmount())
                .isEqualByComparingTo(DecimalUtils.bd("250.00"));

        assertThat(tx.getCeiling())
                .isEqualByComparingTo(DecimalUtils.bd("300.00"));

        assertThat(tx.getRemanent())
                .isEqualByComparingTo(DecimalUtils.bd("50.0"));
    }

    @Test
    void shouldRoundUpCeilingAndComputeRemanentCorrectly() {

        List<Expense> expenses = List.of(
                expense("2023-01-01 10:00:00", 123.45)
        );

        Transaction tx =
                service.buildTransactions(expenses).get(0);

        assertThat(tx.getCeiling())
                .isEqualByComparingTo(DecimalUtils.bd("200.00"));

        assertThat(tx.getRemanent())
                .isEqualByComparingTo(DecimalUtils.bd("76.55"));
    }

    @Test
    void shouldCalculateTotalsCorrectly() {

        List<Transaction> transactions = List.of(
                transaction(
                        "2023-01-01 10:00:00",
                        "100.25",
                        "101.00",
                        "0.75"
                ),
                transaction(
                        "2023-01-02 10:00:00",
                        "200.75",
                        "201.00",
                        "0.25"
                )
        );

        TransactionTotals totals =
                service.calculateTotals(transactions);

        assertThat(totals.amount())
                .isEqualByComparingTo(DecimalUtils.bd("301.00"));

        assertThat(totals.ceiling())
                .isEqualByComparingTo(DecimalUtils.bd("302.00"));

        assertThat(totals.remanent())
                .isEqualByComparingTo(DecimalUtils.bd("1.00"));
    }

    @Test
    void shouldReturnZeroTotalsForEmptyList() {

        TransactionTotals totals =
                service.calculateTotals(List.of());

        assertThat(totals.amount())
                .isEqualByComparingTo(DecimalUtils.ZERO);

        assertThat(totals.ceiling())
                .isEqualByComparingTo(DecimalUtils.ZERO);

        assertThat(totals.remanent())
                .isEqualByComparingTo(DecimalUtils.ZERO);
    }
}
