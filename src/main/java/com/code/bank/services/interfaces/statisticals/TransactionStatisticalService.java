package com.code.bank.services.interfaces.statisticals;


import com.code.bank.api.dtos.responses.transaction.TransactionStatisticalResponse;
import com.code.bank.models.enums.TransactionType;

import java.time.LocalDate;
import java.util.List;

public interface TransactionStatisticalService {
    TransactionStatisticalResponse findTransactionByDate(LocalDate date, TransactionType transactionType);
    TransactionStatisticalResponse findTransactionByWeek(LocalDate startDate,LocalDate endDate, TransactionType transactionType);
    TransactionStatisticalResponse findTransactionByMonth(int month, int year, TransactionType transactionType);
    TransactionStatisticalResponse findTransactionByQuarter(List<Integer> months, int year, TransactionType transactionType);
}
