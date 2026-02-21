package com.blackrock.challenge.investmentengine.service.impl;

import com.blackrock.challenge.investmentengine.domain.Transaction;
import com.blackrock.challenge.investmentengine.service.*;
import com.blackrock.challenge.investmentengine.util.DecimalUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionValidatorServiceImpl
        implements TransactionValidatorService {

    @Override
    public ValidationResult validate(List<Transaction> transactions) {

        Map<LocalDateTime, Transaction> seen = new HashMap<>();
        List<Transaction> valid = new ArrayList<>();
        List<InvalidTransaction> invalid = new ArrayList<>();

        for (Transaction tx : transactions) {

            // 1️⃣ Duplicate check (by date as per document)
            if (seen.containsKey(tx.getDate())) {
                invalid.add(new InvalidTransaction(
                        tx, "Duplicate transaction"));
                continue;
            }

            // 2️⃣ Amount must be positive
            if (tx.getAmount() == null || tx.getAmount().signum() <= 0) {
                invalid.add(new InvalidTransaction(
                        tx, "Amount must be greater than zero"));
                continue;
            }

            // 3️⃣ Ceiling must not be less than amount
            if (tx.getCeiling() == null ||
                    tx.getCeiling().compareTo(tx.getAmount()) < 0) {
                invalid.add(new InvalidTransaction(
                        tx, "Ceiling cannot be less than amount"));
                continue;
            }

            // 4️⃣ Remanent must not be negative
            if (tx.getRemanent() == null ||
                    tx.getRemanent().signum() < 0) {
                invalid.add(new InvalidTransaction(
                        tx, "Remanent cannot be negative"));
                continue;
            }

            // 5️⃣ Check if amount is multiple of 100
            boolean isMultipleOfHundred =
                    tx.getAmount()
                            .remainder(DecimalUtils.bd("100"))
                            .compareTo(BigDecimal.ZERO) == 0;

            if (isMultipleOfHundred) {

                // 5a️⃣ Ceiling must equal amount
                if (tx.getCeiling().compareTo(tx.getAmount()) != 0) {
                    invalid.add(new InvalidTransaction(
                            tx, "Amount is multiple of 100, so ceiling must equal amount"
                    ));
                    continue;
                }

                // 5b️⃣ Remanent must be zero
                if (tx.getRemanent().compareTo(BigDecimal.ZERO) != 0) {
                    invalid.add(new InvalidTransaction(
                            tx,
                            "Amount is multiple of 100, so remanent must be zero"
                    ));
                    continue;
                }

            } else {

                // 6️⃣ Remanent must equal (ceiling - amount)
                BigDecimal expectedRemanent =
                        tx.getCeiling().subtract(tx.getAmount());

                if (tx.getRemanent().compareTo(expectedRemanent) != 0) {
                    invalid.add(new InvalidTransaction(
                            tx,
                            "Remanent must be equal to ceiling minus amount"
                    ));
                    continue;
                }
            }

            // 7️⃣ Mark valid
            seen.put(tx.getDate(), tx);
            valid.add(tx);
        }

        return new ValidationResult(valid, invalid);
    }

}

