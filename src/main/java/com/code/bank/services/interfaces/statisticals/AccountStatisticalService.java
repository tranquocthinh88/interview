package com.code.bank.services.interfaces.statisticals;

import com.code.bank.api.dtos.responses.account.AccountStatisticalResponse;
import com.code.bank.models.Account;

import java.util.List;

public interface AccountStatisticalService {
    AccountStatisticalResponse getAccountsByBalanceCategory(String category);
}
