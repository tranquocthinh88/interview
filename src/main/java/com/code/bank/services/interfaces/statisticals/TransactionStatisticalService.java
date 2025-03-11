package com.code.bank.services.interfaces.statisticals;


import com.code.bank.models.Transaction;
import com.code.bank.models.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionStatisticalService {
    long countTransactionByDay(LocalDateTime date, TransactionType transactionType);
    long countTransactionByWeek(LocalDateTime startDate,LocalDateTime endDate, TransactionType transactionType);
    long countTransactionByMonth(int month, int year, TransactionType transactionType);
    long countTransactionByQuarter(List<Integer> months, int year, TransactionType transactionType);
}
