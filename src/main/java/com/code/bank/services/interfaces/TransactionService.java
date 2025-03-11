package com.code.bank.services.interfaces;

import com.code.bank.api.dtos.requests.TransactionDto;
import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.models.Transaction;
import com.code.bank.models.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransactionService extends BaseService<Transaction, String> {
    Transaction createTransaction(String accountNumber, TransactionDto transactionDto) throws DataNotFoundException;
    Page<Transaction> searchTransactions(BigDecimal minAmount, BigDecimal maxAmount, TransactionType transactionType,
                                         LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
}
