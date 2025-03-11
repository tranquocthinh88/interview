package com.code.bank.api.controllers;

import com.code.bank.api.dtos.requests.TransactionDto;
import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.api.mappers.TransactionMapper;
import com.code.bank.models.Account;
import com.code.bank.models.Transaction;
import com.code.bank.repositories.AccountRepository;
import com.code.bank.services.interfaces.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.DateTimeException;
import com.code.bank.models.enums.TransactionType;
import com.code.bank.repositories.AccountRepository;
import com.code.bank.services.interfaces.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    @PostMapping
    public Response addTransaction(@RequestBody @Valid TransactionDto transactionDto, String accountNumber) throws Exception{
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "add transaction successful",
                transactionService.createTransaction(accountNumber, transactionDto));
    }


    @GetMapping
    public Response getAllTransactions() {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get all transaction successfully",
                transactionService.findAll());
    }

    @GetMapping("/{id}")
    public Response getTransactionById(@PathVariable String id) throws Exception {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get transaction by id successfully",
                transactionService.findById(id).orElseThrow(() -> new DataNotFoundException("transaction not found")));
    }

    @DeleteMapping("/{id}")
    public Response deleteTransaction(@PathVariable String id) throws Exception {
        transactionService.delete(id);
        return new ResponseSuccess<>(HttpStatus.NO_CONTENT.value(),
                "delete transaction successfully" + id);
    }

    @PutMapping("/{id}")
    public Response updateTransaction(@PathVariable String id, @RequestBody @Valid TransactionDto transactionDto) throws Exception {
        Transaction transaction = transactionMapper.Transaction2DtoTransaction(transactionDto);
        transaction.setId(id);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "update transaction successfully",
                transactionService.update(id, transaction));
    }

    @PatchMapping("/{id}")
    public Response patchTransaction(@PathVariable String id, @RequestBody @Valid Map<String, ?> data) throws Exception {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "update transaction successfully",
                transactionService.updatePatch(id,data));
    }

    @GetMapping("/search")
    public Page<Transaction> searchTransactions(
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) TransactionType transactionType,
            @RequestParam(required = false) LocalDateTime fromDate,
            @RequestParam(required = false) LocalDateTime toDate,
            Pageable pageable
    ) {
        return transactionService.searchTransactions(minAmount, maxAmount, transactionType, fromDate, toDate, pageable);
    }

}
