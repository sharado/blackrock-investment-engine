package com.blackrock.challenge.investmentengine.api.controller;

import com.blackrock.challenge.investmentengine.api.dto.*;
import com.blackrock.challenge.investmentengine.domain.Transaction;
import com.blackrock.challenge.investmentengine.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blackrock/challenge/v1")
public class TransactionValidatorController {

    private final TransactionValidatorService validatorService;

    @PostMapping("/transactions:validator")
    public TransactionValidationResponseDto validate(
            @RequestBody TransactionValidationRequestDto request
    ) {

        // 1️⃣ Map DTO → Domain
        List<Transaction> domainTransactions =
                request.transactions().stream()
                        .map(TransactionMapper::toDomain)
                        .toList();

        // 2️⃣ Validate
        ValidationResult result =
                validatorService.validate(domainTransactions);

        // 3️⃣ Map Domain → DTO
        List<TransactionDto> valid =
                result.valid().stream()
                        .map(TransactionMapper::toDto)
                        .toList();

        List<InvalidTransactionDto> invalid =
                result.invalid().stream()
                        .map(inv -> TransactionMapper.toInvalidDto(
                                inv.transaction(),
                                inv.message()
                        ))
                        .toList();

        // 4️⃣ Return
        return TransactionValidationResponseDto.builder()
                .valid(valid)
                .invalid(invalid)
                .build();
    }
}

