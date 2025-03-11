package com.code.bank.api.statisticals;

import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.services.interfaces.statisticals.AccountStatisticalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistical/accounts")
public class AccountStatistical {

    private final AccountStatisticalService accountStatisticalService;


    public AccountStatistical(AccountStatisticalService accountStatisticalService) {
        this.accountStatisticalService = accountStatisticalService;
    }

    @GetMapping("/balance-category")
    public Response getBalanceCategory(@RequestParam String category) {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "get balance by category successfully",
                accountStatisticalService.getAccountsByBalanceCategory(category));
    }
}
