package com.code.bank.services.interfaces;

import com.code.bank.models.Account;

import java.util.Optional;

public interface AccountService extends BaseService<Account, Integer>  {
    Optional<Account> findAccountByCustomerId(Integer id);
}
