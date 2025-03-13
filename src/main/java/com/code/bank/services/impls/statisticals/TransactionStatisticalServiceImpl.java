package com.code.bank.services.impls.statisticals;

import com.code.bank.api.dtos.responses.transaction.TransactionStatisticalResponse;
import com.code.bank.models.enums.TransactionType;
import com.code.bank.repositories.TransactionRepository;
import com.code.bank.services.interfaces.statisticals.TransactionStatisticalService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionStatisticalServiceImpl implements TransactionStatisticalService {

    private final TransactionRepository transactionRepository;

    public TransactionStatisticalServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionStatisticalResponse findTransactionByDate(LocalDate date, TransactionType transactionType) {
        TransactionStatisticalResponse response = new TransactionStatisticalResponse();
        response.setCount(transactionRepository.countTransactionByDay(date, transactionType));
        response.setTransactions(transactionRepository.findTransactionByDate(date, transactionType));
        return response;
    }

    @Override
    public TransactionStatisticalResponse findTransactionByWeek(LocalDate startDate, LocalDate endDate, TransactionType transactionType) {
        TransactionStatisticalResponse response = new TransactionStatisticalResponse();
        response.setCount(transactionRepository.countTransactionByWeek(startDate, endDate, transactionType));
        response.setTransactions(transactionRepository.findTransactionByWeek(startDate, endDate, transactionType));
        return response;
    }

    @Override
    public TransactionStatisticalResponse findTransactionByMonth(int month, int year, TransactionType transactionType) {
        TransactionStatisticalResponse response = new TransactionStatisticalResponse();
        response.setCount(transactionRepository.countTransactionByMonth(month, year, transactionType));
        response.setTransactions(transactionRepository.findTransactionByMonth(month, year, transactionType));
        return response;
    }

    @Override
    public TransactionStatisticalResponse findTransactionByQuarter(List<Integer> months, int year, TransactionType transactionType) {
        TransactionStatisticalResponse response = new TransactionStatisticalResponse();
        response.setCount(transactionRepository.countTransactionByQuarter(months, year, transactionType));
        response.setTransactions(transactionRepository.findTransactionByQuarter(months, year, transactionType));
        return response;
    }


}
