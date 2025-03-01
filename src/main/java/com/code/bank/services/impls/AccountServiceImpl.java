package com.code.bank.services.impls;

import com.code.bank.models.Account;
import com.code.bank.services.interfaces.AccountService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Integer> implements AccountService {
    public AccountServiceImpl(JpaRepository<Account, Integer> repository) {
        super(repository, Account.class);
    }
}
