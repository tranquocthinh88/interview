package com.code.bank.api.statisticals;

import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.services.interfaces.statisticals.AccountStatisticalService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistical/accounts")
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class AccountStatistical {

    private final AccountStatisticalService accountStatisticalService;


    public AccountStatistical(AccountStatisticalService accountStatisticalService) {
        this.accountStatisticalService = accountStatisticalService;
    }

    @GetMapping("/balance-category")
    public Response getBalanceCategory(@RequestParam String category, @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "get balance by category successfully",
                accountStatisticalService.getAccountsByBalanceCategory(category, pageable));
    }
}
