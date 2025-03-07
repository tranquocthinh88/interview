package com.code.bank.services.interfaces.statisticals;

import com.code.bank.models.Account;

import java.util.List;

public interface AccountStatisticalService {
    List<Account> getAccountsByBalanceCategory(String category);
}
