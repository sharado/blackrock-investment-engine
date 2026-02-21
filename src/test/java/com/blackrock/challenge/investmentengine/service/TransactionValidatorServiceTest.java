package com.blackrock.challenge.investmentengine.service;

import com.blackrock.challenge.investmentengine.domain.Transaction;
import com.blackrock.challenge.investmentengine.service.impl.TransactionValidatorServiceImpl;
import com.blackrock.challenge.investmentengine.util.DecimalUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionValidatorServiceTest {

    private TransactionValidatorService service;

    @BeforeEach
    void setUp() {
        service = new TransactionValidatorServiceImpl();
    }

    private Transaction tx(String date, String amount, String ceiling, String remanent) {
        return Transaction.builder()
                .date(LocalDateTime.parse(date))
                .amount(DecimalUtils.bd(amount))
                .ceiling(DecimalUtils.bd(ceiling))
                .remanent(DecimalUtils.bd(remanent))
                .build();
    }

    // ============================================================
    // Duplicate detection
    // ============================================================

    @Test
    void shouldDetectDuplicateTransactions() {

        Transaction t1 = tx("2023-01-01T10:00", "100", "100", "0");
        Transaction t2 = tx("2023-01-01T10:00", "150", "200", "50");

        ValidationResult result =
                service.validate(List.of(t1, t2));

        assertThat(result.valid()).hasSize(1);
        assertThat(result.invalid()).hasSize(1);

        assertThat(result.invalid().get(0).message())
                .isEqualTo("Duplicate transaction");
    }



    // ============================================================
    // Negative amount
    // ============================================================

    @Test
    void shouldRejectNegativeAmount() {

        Transaction t = tx("2023-01-02T10:00", "-10", "100", "110");

        ValidationResult result =
                service.validate(List.of(t));

        assertThat(result.valid()).isEmpty();
        assertThat(result.invalid()).hasSize(1);
    }

    // ============================================================
    // Valid transaction
    // ============================================================

    @Test
    void shouldAcceptValidTransaction() {

        Transaction t = tx(
                "2023-01-03T10:00",
                "100",
                "100",
                "0"
        );

        ValidationResult result =
                service.validate(List.of(t));

        assertThat(result.valid()).hasSize(1);
        assertThat(result.invalid()).isEmpty();
    }


    // ============================================================
    // 1️⃣ multiple of 100 — VALID
    // ============================================================

    @Test
    void shouldAcceptTransactionWhenAmountIsMultipleOf100() {

        Transaction transaction = tx(
                "2023-01-01T10:00:00",
                "200.00",
                "200.00",
                "0.00"
        );

        ValidationResult result =
                service.validate(List.of(transaction));

        assertThat(result.valid()).hasSize(1);
        assertThat(result.invalid()).isEmpty();
    }

    // ============================================================
    // 2️⃣ multiple of 100 — INVALID ceiling
    // ============================================================

    @Test
    void shouldRejectWhenAmountIsMultipleOf100ButCeilingIsDifferent() {

        Transaction transaction = tx(
                "2023-01-01T10:00:00",
                "300.00",
                "400.00", // ❌ invalid
                "0.00"
        );

        ValidationResult result =
                service.validate(List.of(transaction));

        assertThat(result.valid()).isEmpty();
        assertThat(result.invalid()).hasSize(1);

        assertThat(result.invalid().get(0).message())
                .contains("ceiling");
    }

    // ============================================================
    // 3️⃣ non-multiple of 100 — WRONG remanent
    // ============================================================

    @Test
    void shouldRejectWhenRemanentIsNotCeilingMinusAmount() {

        Transaction transaction = tx(
                "2023-01-01T10:00:00",
                "123.45",
                "200.00",
                "50.00" // ❌ should be 76.55
        );

        ValidationResult result =
                service.validate(List.of(transaction));

        assertThat(result.valid()).isEmpty();
        assertThat(result.invalid()).hasSize(1);

        assertThat(result.invalid().get(0).message())
                .contains("Remanent");
    }
}
