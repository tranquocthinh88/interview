package com.code.bank.services.impls.statisticals;

import com.code.bank.api.dtos.responses.transaction.TransactionStatisticalResponse;
import com.code.bank.models.Transaction;
import com.code.bank.models.enums.TransactionType;
import com.code.bank.repositories.TransactionRepository;
import com.code.bank.services.interfaces.statisticals.TransactionStatisticalService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionStatisticalServiceImpl implements TransactionStatisticalService {

    private final TransactionRepository transactionRepository;

    public TransactionStatisticalServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionStatisticalResponse findTransactionByDate(LocalDate date, TransactionType transactionType, Pageable pageable) {
        TransactionStatisticalResponse response = new TransactionStatisticalResponse();

        // Lấy tổng số giao dịch trong ngày
        long totalCount = transactionRepository.countTransactionByDay(date, transactionType);
        response.setCount(totalCount); // Gán tổng số giao dịch

        // Lấy danh sách giao dịch theo trang
        List<Transaction> transactions = transactionRepository.findTransactionByDate(date, transactionType, pageable).getContent();
        response.setTransactions(transactions); // Gán danh sách giao dịch

        // Lấy toàn bộ giao dịch trong ngày để tính toán thống kê (bỏ qua phân trang)
        List<Transaction> allTransactions = transactionRepository.findAllByDate(date, transactionType);

        // Tính toán số liệu thống kê
        Map<String, Double> statistics = calculateStatistics(allTransactions);

        // Thiết lập số liệu vào response
        response.setAverage(statistics.get("average"));
        response.setMax(statistics.get("max"));
        response.setMin(statistics.get("min"));

        // Lấy danh sách giao dịch của trang hiện tại
        List<Transaction> paginatedTransactions = transactionRepository.findTransactionByDate(date, transactionType, pageable).getContent();
        response.setTransactions(paginatedTransactions);

        return response;
    }

    @Override
    public TransactionStatisticalResponse findTransactionByWeek(LocalDate startDate, LocalDate endDate, TransactionType transactionType, Pageable pageable) {
        TransactionStatisticalResponse response = new TransactionStatisticalResponse();
        long totalCount = transactionRepository.countTransactionByWeek(startDate, endDate, transactionType);
        response.setCount(totalCount);
        List<Transaction> transactions = transactionRepository.findTransactionByWeek(startDate, endDate, transactionType, pageable).getContent();
        response.setTransactions(transactions);
        List<Transaction> allTransactions = transactionRepository.findAllByWeek(startDate, endDate, transactionType);
        Map<String, Double> statistics = calculateStatistics(allTransactions);
        response.setAverage(statistics.get("average"));
        response.setMax(statistics.get("max"));
        response.setMin(statistics.get("min"));
        List<Transaction> paginatedTransactions = transactionRepository.findTransactionByWeek(startDate, endDate, transactionType, pageable).getContent();
        response.setTransactions(paginatedTransactions);
        return response;
    }

    @Override
    public TransactionStatisticalResponse findTransactionByMonth(int month, int year, TransactionType transactionType, Pageable pageable) {
        TransactionStatisticalResponse response = new TransactionStatisticalResponse();
        long totalCount = transactionRepository.countTransactionByMonth(month, year, transactionType);
        response.setCount(totalCount);
        List<Transaction> transactions = transactionRepository.findTransactionByMonth(month, year, transactionType, pageable).getContent();
        response.setTransactions(transactions);
        List<Transaction> allTransactions = transactionRepository.findAllByMonth(month, year, transactionType);
        Map<String, Double> statistics = calculateStatistics(allTransactions);
        response.setAverage(statistics.get("average"));
        response.setMax(statistics.get("max"));
        response.setMin(statistics.get("min"));
        List<Transaction> paginatedTransactions = transactionRepository.findTransactionByMonth(month, year, transactionType, pageable).getContent();
        response.setTransactions(paginatedTransactions);
        return response;
    }

    @Override
    public TransactionStatisticalResponse findTransactionByQuarter(List<Integer> months, int year, TransactionType transactionType, Pageable pageable) {
        TransactionStatisticalResponse response = new TransactionStatisticalResponse();
        long totalCount = transactionRepository.countTransactionByQuarter(months, year, transactionType);
        response.setCount(totalCount);
        List<Transaction> transactions = transactionRepository.findTransactionByQuarter(months, year, transactionType, pageable).getContent();
        response.setTransactions(transactions);
        List<Transaction> allTransactions = transactionRepository.findAllByQuarter(months, year, transactionType);
        Map<String, Double> statistics = calculateStatistics(allTransactions);
        response.setAverage(statistics.get("average"));
        response.setMax(statistics.get("max"));
        response.setMin(statistics.get("min"));
        List<Transaction> paginatedTransactions = transactionRepository.findTransactionByQuarter(months, year, transactionType, pageable).getContent();
        response.setTransactions(paginatedTransactions);
        return response;
    }

    @Override
    public TransactionStatisticalResponse findTransactionByYear(int year, TransactionType transactionType, Pageable pageable) {
        TransactionStatisticalResponse response = new TransactionStatisticalResponse();
        long totalCount = transactionRepository.countTransactionByYear(year, transactionType);
        response.setCount(totalCount);
        List<Transaction> transactions = transactionRepository.findTransactionByYear(year, transactionType, pageable).getContent();
        response.setTransactions(transactions);
        List<Transaction> allTransactions = transactionRepository.findAllByYear(year, transactionType);
        Map<String, Double> statistics = calculateStatistics(allTransactions);
        response.setAverage(statistics.get("average"));
        response.setMax(statistics.get("max"));
        response.setMin(statistics.get("min"));
        List<Transaction> paginatedTransactions = transactionRepository.findTransactionByYear(year, transactionType, pageable).getContent();
        response.setTransactions(paginatedTransactions);
        return response;
    }

    // Thêm logic tính số liệu giao dịch
    private Map<String, Double> calculateStatistics(List<Transaction> transactions) {
        double sum = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        double avg = transactions.isEmpty() ? 0 : sum / transactions.size();
        double max = transactions.stream().mapToDouble(Transaction::getAmount).max().orElse(0);
        double min = transactions.stream().mapToDouble(Transaction::getAmount).min().orElse(0);

        Map<String, Double> statistics = new HashMap<>();
        statistics.put("average", avg);
        statistics.put("max", max);
        statistics.put("min", min);

        return statistics;
    }

}
