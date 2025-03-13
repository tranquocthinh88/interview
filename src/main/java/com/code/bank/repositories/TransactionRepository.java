package com.code.bank.repositories;

import com.code.bank.models.Transaction;
import com.code.bank.models.enums.TransactionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, String> {
    // Thống kê theo ngày cùng với loại giao dịch (DEPOSIT, WITHDRAWAL, TRANSFER)
    @Query("SELECT COUNT(trans) FROM Transaction trans WHERE FUNCTION('DATE', trans.transactionDate) = FUNCTION('DATE', :date) AND trans.transactionType = :transactionType")
    long countTransactionByDay(@Param("date") LocalDate date, @Param("transactionType")TransactionType transactionType);
    @Query("SELECT trans FROM Transaction trans WHERE FUNCTION('DATE', trans.transactionDate) = FUNCTION('DATE', :date) AND trans.transactionType = :transactionType")
    List<Transaction> findTransactionByDate(@Param("date") LocalDate date, @Param("transactionType")TransactionType transactionType);
    // Thống kê theo tuần cùng với loại giao dịch (DEPOSIT, WITHDRAWAL, TRANSFER)
    @Query("SELECT COUNT(trans) FROM Transaction trans WHERE trans.transactionDate BETWEEN :startDate AND :endDate AND trans.transactionType = :transactionType")
    long countTransactionByWeek(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate, @Param("transactionType")TransactionType transactionType);
    // Thống kê theo tháng cùng với loại giao dịch (DEPOSIT, WITHDRAWAL, TRANSFER)
    @Query("SELECT COUNT(trans) FROM Transaction trans WHERE YEAR(trans.transactionDate) = :year AND MONTH(trans.transactionDate) = :month AND trans.transactionType = :transactionType")
    long countTransactionByMonth(@Param("year") int year, @Param("month") int month, @Param("transactionType")TransactionType transactionType);
    // Thống kê theo quý cùng với loại giao dịch (DEPOSIT, WITHDRAWAL, TRANSFER)
    @Query("SELECT COUNT(trans) FROM Transaction trans " +
            "WHERE YEAR(trans.transactionDate) = :year " +
            "AND MONTH(trans.transactionDate) IN (:months) " +
            "AND trans.transactionType = :transactionType")
    long countTransactionByQuarter(@Param("months") List<Integer> months,@Param("year") int year, @Param("transactionType")TransactionType transactionType);

}