package com.code.bank.api.controllers;


import com.code.bank.api.dtos.requests.AccountDto;
import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.api.mappers.AccountMapper;
import com.code.bank.models.Account;
import com.code.bank.models.Customer;
import com.code.bank.repositories.CustomerRepository;
import com.code.bank.services.interfaces.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;
    private final AccountService accountService;

    @PostMapping
    public Response addAccount(@RequestBody @Valid AccountDto accountDto) {
        Account account = accountMapper.AccountDto2Account(accountDto);
        Customer customer = customerRepository.findById(accountDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        account.setCustomer(customer);
        return new ResponseSuccess<>(HttpStatus.OK.value(), "create account successfully",
                accountService.save(account));
    }

    @GetMapping
    public Response getAllAccount() {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all accounts successfully",
                accountService.findAll()
        );
    }

    @GetMapping("/{id}")
    public Response getAccountById(@PathVariable int id) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get account by id successfully",
                accountService.findById(id).orElseThrow(()-> new DataNotFoundException("Account not found"))
        );
    }

    @DeleteMapping("/{id}")
    public Response deleteAccount(@PathVariable int id) throws DataNotFoundException {
        accountService.delete(id);
        return new ResponseSuccess<>(HttpStatus.NO_CONTENT.value(),
                "delete account successfully" + id);
    }

    @PatchMapping("/{id}")
    public Response patchAccount(@PathVariable int id, @RequestBody @Valid Map<String, ?> data) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "account updated successfully",
                accountService.updatePatch(id, data)
        );
    }

    @PutMapping("/{id}")
    public Response updateAccount(@PathVariable int id ,@RequestBody @Valid AccountDto accountDto) throws Exception {
        Account account = accountMapper.AccountDto2Account(accountDto);
        account.setId(id);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "account updated successfully",
                accountService.update(id, account));
    }
}
