package com.code.bank.services.impls;

import com.code.bank.models.Account;
import com.code.bank.repositories.AccountRepository;
import com.code.bank.services.interfaces.AccountService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Integer> implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(JpaRepository<Account, Integer> repository, AccountRepository accountRepository) {
        super(repository, Account.class);
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findAccountByCustomerId(Integer id) {
        return accountRepository.findByCustomer_Id(id);
    }
}
