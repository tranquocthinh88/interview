package com.code.bank.repositories;

import com.code.bank.models.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface AccountRepository extends BaseRepository<Account, Integer> {
    Optional<Account> findByCustomer_Id(Integer id);
    Optional<Account> findByAccountNumber(String accountNumber);

    long countAccountByBalanceLessThan(double balance);
    Page<Account> findAccountByBalanceLessThan(double balance, Pageable pageable); // tài khoản số dư thấp
    long countAccountByBalanceBetween(double minBalance, double maxBalance);
    Page<Account> findAccountByBalanceBetween(double minBalance, double maxBalance, Pageable pageable);
    long countAccountByBalanceGreaterThan(double balance);
    Page<Account> findAccountByBalanceGreaterThan(double balance, Pageable pageable);

}