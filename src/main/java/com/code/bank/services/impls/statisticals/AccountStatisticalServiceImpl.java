package com.code.bank.services.impls.statisticals;

import com.code.bank.api.dtos.responses.account.AccountStatisticalResponse;
import com.code.bank.models.Account;
import com.code.bank.repositories.AccountRepository;
import com.code.bank.services.interfaces.statisticals.AccountStatisticalService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountStatisticalServiceImpl implements AccountStatisticalService {

    private final AccountRepository accountRepository;

    public AccountStatisticalServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountStatisticalResponse getAccountsByBalanceCategory(String category, Pageable pageable) {
        List<Account> accounts;
        long count = switch (category.toLowerCase()) {
            case "low" -> {
                accounts = accountRepository.findAccountByBalanceLessThan(5000000, pageable).getContent();
                yield accountRepository.countAccountByBalanceLessThan(5000000);
            }
            case "medium" -> {
                accounts = accountRepository.findAccountByBalanceBetween(5000000, 50000000, pageable).getContent();
                yield accountRepository.countAccountByBalanceBetween(5000000, 50000000);
            }
            case "high" -> {
                accounts = accountRepository.findAccountByBalanceGreaterThan(50000000, pageable).getContent();
                yield accountRepository.countAccountByBalanceGreaterThan(50000000);
            }
            default -> throw new IllegalArgumentException("Invalid category. Choose from: high, medium, low.");
        };

        return new AccountStatisticalResponse(count, accounts);
    }

}
