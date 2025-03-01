package com.code.bank.services.impls;

import com.code.bank.models.Transaction;
import com.code.bank.services.interfaces.TransactionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction, String> implements TransactionService {
    public TransactionServiceImpl(JpaRepository<Transaction, String> repository, Class<Transaction> entityClass) {
        super(repository, entityClass);
    }
}
