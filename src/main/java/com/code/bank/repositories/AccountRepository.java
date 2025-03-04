package com.code.bank.repositories;

import com.code.bank.models.Account;

import java.util.Optional;

public interface AccountRepository extends BaseRepository<Account, Integer> {
    Optional<Account> findByCustomer_Id(Integer id);
    Optional<Account> findByAccountNumber(String accountNumber);
}