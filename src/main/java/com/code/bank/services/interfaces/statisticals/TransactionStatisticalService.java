package com.code.bank.services.interfaces.statisticals;


import com.code.bank.api.dtos.responses.transaction.TransactionStatisticalResponse;
import com.code.bank.models.enums.TransactionType;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionStatisticalService {
    TransactionStatisticalResponse findTransactionByDate(LocalDate date, TransactionType transactionType, Pageable pageable);
    TransactionStatisticalResponse findTransactionByWeek(LocalDate startDate,LocalDate endDate, TransactionType transactionType, Pageable pageable);
    TransactionStatisticalResponse findTransactionByMonth(int month, int year, TransactionType transactionType, Pageable pageable);
    TransactionStatisticalResponse findTransactionByQuarter(List<Integer> months, int year, TransactionType transactionType, Pageable pageable);
    TransactionStatisticalResponse findTransactionByYear(int year, TransactionType transactionType, Pageable pageable);
}
