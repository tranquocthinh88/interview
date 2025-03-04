package com.code.bank.services.interfaces;

import com.code.bank.api.dtos.requests.TransactionDto;
import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.models.Transaction;

public interface TransactionService extends BaseService<Transaction, String> {
    Transaction createTransaction(String accountNumber, TransactionDto transactionDto) throws DataNotFoundException;
}
