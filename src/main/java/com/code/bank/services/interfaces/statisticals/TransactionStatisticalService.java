package com.code.bank.services.interfaces.statisticals;


import com.code.bank.api.dtos.responses.TransactionStatisticalResponse;
import com.code.bank.models.Transaction;
import com.code.bank.models.enums.TransactionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionStatisticalService {
    TransactionStatisticalResponse findTransactionByDate(LocalDate date, TransactionType transactionType);
    long countTransactionByWeek(LocalDate startDate,LocalDate endDate, TransactionType transactionType);
    long countTransactionByMonth(int month, int year, TransactionType transactionType);
    long countTransactionByQuarter(List<Integer> months, int year, TransactionType transactionType);
}
