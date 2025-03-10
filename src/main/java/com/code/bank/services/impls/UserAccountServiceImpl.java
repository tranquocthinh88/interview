package com.code.bank.services.impls;

import com.code.bank.models.UserAccount;
import com.code.bank.services.interfaces.UserAccountService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl extends  BaseServiceImpl<UserAccount, Integer> implements UserAccountService {
    public UserAccountServiceImpl(JpaRepository<UserAccount, Integer> repository) {
        super(repository, UserAccount.class);
    }
}
