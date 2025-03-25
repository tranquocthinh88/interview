package com.code.bank.services.interfaces;

import com.code.bank.models.Account;
import com.code.bank.models.enums.AccountStatus;

import java.util.List;
import java.util.Optional;

public interface AccountService extends BaseService<Account, Integer>  {
    Optional<Account> findAccountByCustomerId(Integer id);
    void changeAccountStatus(String accountNumber, AccountStatus accountNewStatus);
    List<Account> getAllAccountRedis();
    void updateAccountRedis(Account account);
}
