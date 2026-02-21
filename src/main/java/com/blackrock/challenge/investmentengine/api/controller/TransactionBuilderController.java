package com.blackrock.challenge.investmentengine.api.controller;

import com.blackrock.challenge.investmentengine.api.dto.ExpenseRequestDto;
import com.blackrock.challenge.investmentengine.api.dto.TransactionParseResponseDto;
import com.blackrock.challenge.investmentengine.domain.Expense;
import com.blackrock.challenge.investmentengine.domain.Transaction;
import com.blackrock.challenge.investmentengine.service.TransactionBuilderService;
import com.blackrock.challenge.investmentengine.service.TransactionMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blackrock/challenge/v1")
public class TransactionBuilderController {

    private final TransactionBuilderService service;

    public TransactionBuilderController(TransactionBuilderService service) {
        this.service = service;
    }

    @PostMapping("/transactions:parse")
    public TransactionParseResponseDto parse(
            @RequestBody List<ExpenseRequestDto> request) {

        var expenses = TransactionMapper.toExpenses(request);

        List<Transaction> transactions = service.buildTransactions(expenses);

        return TransactionParseResponseDto.builder()
                .transactions(transactions.stream()
                        .map(TransactionMapper::toDto)
                        .toList()
                )
                .totals(TransactionMapper.toDto(service.calculateTotals(transactions))
                )
                .build();
    }
}
