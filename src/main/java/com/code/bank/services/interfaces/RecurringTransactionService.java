package com.code.bank.services.interfaces;

import com.code.bank.api.dtos.requests.RecurringTransactionDto;
import com.code.bank.models.RecurringTransaction;

public interface RecurringTransactionService {
    RecurringTransaction createRecurringTransaction(RecurringTransactionDto recurringTransactionDto);
    void processRecurringTransactions();
}
