package com.code.bank.services.interfaces.statisticals;

import com.code.bank.api.dtos.responses.account.AccountStatisticalResponse;
import org.springframework.data.domain.Pageable;

public interface AccountStatisticalService {
    AccountStatisticalResponse getAccountsByBalanceCategory(String category, Pageable pageable);
}
