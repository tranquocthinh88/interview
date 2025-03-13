package com.code.bank.repositories;

import com.code.bank.models.Account;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends BaseRepository<Account, Integer> {
    Optional<Account> findByCustomer_Id(Integer id);
    Optional<Account> findByAccountNumber(String accountNumber);

    long countAccountByBalanceLessThan(double balance);
    List<Account> findAccountByBalanceLessThan(double balance); // tài khoản số dư thấp
    long countAccountByBalanceBetween(double minBalance, double maxBalance);
    List<Account> findAccountByBalanceBetween(double minBalance, double maxBalance);
    long countAccountByBalanceGreaterThan(double balance);
    List<Account> findAccountByBalanceGreaterThan(double balance);
}