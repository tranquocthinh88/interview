package com.code.bank.repositories;

import com.code.bank.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends BaseRepository<Transaction, String> {
}