package com.code.bank.services.impls;

import com.code.bank.models.Account;
import com.code.bank.models.AccountStatusHistory;
import com.code.bank.models.UserAccount;
import com.code.bank.models.enums.AccountStatus;
import com.code.bank.models.enums.Role;
import com.code.bank.repositories.AccountRepository;
import com.code.bank.repositories.AccountStatusHistoryRepository;
import com.code.bank.repositories.UserAccountRepository;
import com.code.bank.services.interfaces.AccountService;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.code.bank.repositories.AccountRepository;
import com.code.bank.services.interfaces.AccountService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Integer> implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountStatusHistoryRepository accountStatusHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;

    public AccountServiceImpl(JpaRepository<Account, Integer> repository, AccountRepository accountRepository, AccountStatusHistoryRepository accountStatusHistoryRepository, PasswordEncoder passwordEncoder, UserAccountRepository userAccountRepository) {
        super(repository, Account.class);
        this.accountRepository = accountRepository;
        this.accountStatusHistoryRepository = accountStatusHistoryRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public Optional<Account> findAccountByCustomerId(Integer id) {
        return accountRepository.findByCustomer_Id(id);
    }

    @Override
    public void changeAccountStatus(String accountNumber, AccountStatus accountNewStatus) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        AccountStatus oldStatus = account.getAccountStatus();

        if (oldStatus != accountNewStatus) {
            AccountStatusHistory history = new AccountStatusHistory(account, oldStatus, accountNewStatus);
            accountStatusHistoryRepository.save(history);

            account.setAccountStatus(accountNewStatus);
            accountRepository.save(account);
        }
    }

    @PostConstruct
    public void createAdminAccount() {
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername("19009000");
        userAccount.setPassword(passwordEncoder.encode("admin"));
        userAccount.setRole(Role.ADMIN);
        userAccount.setVerify(true);
        if (!userAccountRepository.existsByUsername(userAccount.getUsername())) {
            userAccountRepository.save(userAccount);
        }
    }

}
