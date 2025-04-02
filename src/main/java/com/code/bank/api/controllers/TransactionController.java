package com.code.bank.api.controllers;

import com.code.bank.api.dtos.requests.TransactionDto;
import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.api.mappers.TransactionMapper;
import com.code.bank.models.Transaction;
import com.code.bank.models.enums.SortDirection;
import com.code.bank.services.interfaces.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.code.bank.models.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

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

    @GetMapping("/search")
    public Page<Transaction> searchTransactions(
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) TransactionType transactionType,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "amount") String sortBy,
            @RequestParam(required = false) SortDirection sortDirection

    ) {
        Pageable pageable;
        if (sortBy != null && sortDirection != null) {
            Sort sort = (sortDirection == SortDirection.DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
            pageable = PageRequest.of(page, size, sort);
        } else {
            pageable = PageRequest.of(page, size); // Không sắp xếp, giữ nguyên thứ tự mặc định từ DB
        }
        return transactionService.searchTransactions(minAmount, maxAmount, transactionType, fromDate, toDate, pageable);
    }

    @GetMapping("/redis/{accountId}")
    public Response getTransactionByAccountId(@PathVariable int accountId) {
        long startTime = System.currentTimeMillis();
        Response response = new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get transaction by account id use redis successfully",
                transactionService.getTransactionsByAccountId(accountId)
        );
        long endTime = System.currentTimeMillis();
        log.info("getTransactionByAccountId() using redis executed in {} ms", (endTime - startTime));
        return response;
    }

}
