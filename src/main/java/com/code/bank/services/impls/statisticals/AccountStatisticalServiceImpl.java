package com.code.bank.services.impls.statisticals;

import com.code.bank.models.Account;
import com.code.bank.repositories.AccountRepository;
import com.code.bank.services.interfaces.statisticals.AccountStatisticalService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountStatisticalServiceImpl implements AccountStatisticalService {

    private final AccountRepository accountRepository;

    public AccountStatisticalServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAccountsByBalanceCategory(String category) {
        return switch (category.toLowerCase()) {
            case "low" -> accountRepository.findAccountByBalanceLessThan(5000000);
            case "medium" -> accountRepository.findAccountByBalanceBetween(5000000, 50000000);
            case "high" -> accountRepository.findAccountByBalanceGreaterThan(50000000);
            default -> throw new IllegalArgumentException("Invalid category. Choose from: high, medium, low.");
        };
    }
}
