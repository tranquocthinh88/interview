package com.code.bank.services.impls.statisticals;

import com.code.bank.models.Transaction;
import com.code.bank.models.enums.TransactionType;
import com.code.bank.repositories.TransactionRepository;
import com.code.bank.services.interfaces.statisticals.TransactionStatisticalService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionStatisticalServiceImpl implements TransactionStatisticalService {

    private final TransactionRepository transactionRepository;

    public TransactionStatisticalServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public long countTransactionByDay(LocalDateTime date, TransactionType transactionType) {
        return transactionRepository.countTransactionByDay(date, transactionType);
    }

    @Override
    public long countTransactionByWeek(LocalDateTime startDate, LocalDateTime endDate, TransactionType transactionType) {
        return transactionRepository.countTransactionByWeek(startDate, endDate, transactionType);
    }

    @Override
    public long countTransactionByMonth(int month, int year, TransactionType transactionType) {
        return transactionRepository.countTransactionByMonth(month, year, transactionType);
    }

    @Override
    public long countTransactionByQuarter(List<Integer> months, int year, TransactionType transactionType) {
        return transactionRepository.countTransactionByQuarter(months, year, transactionType);
    }

}
