package com.code.bank.api.controllers;


import com.code.bank.api.dtos.requests.RecurringTransactionDto;
import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.services.interfaces.RecurringTransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recurringTransaction")
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
@RequiredArgsConstructor
public class RecurringTransactionController {

    private final RecurringTransactionService recurringTransactionService;

    @PostMapping
    public Response createRecurringTransaction(@RequestBody RecurringTransactionDto recurringTransactionDto) {
        return new ResponseSuccess<>(HttpStatus.CREATED.value(),
                "create recurring transaction successfully",
                recurringTransactionService.createRecurringTransaction(recurringTransactionDto)
                );
    }
}
