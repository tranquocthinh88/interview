package com.code.bank.api.controllers;


import com.code.bank.api.dtos.requests.AccountDto;
import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.api.mappers.AccountMapper;
import com.code.bank.models.Account;
import com.code.bank.models.Customer;
import com.code.bank.models.enums.AccountStatus;
import com.code.bank.repositories.AccountRepository;
import com.code.bank.repositories.CustomerRepository;
import com.code.bank.services.interfaces.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import com.code.bank.api.mappers.AddressMapper;
import com.code.bank.models.Address;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirements({@SecurityRequirement(name = "bearerAuth")})
public class AccountController {
    private final AccountMapper accountMapper;
    private final CustomerRepository customerRepository;
    private final AccountService accountService;
    private final AddressMapper addressMapper;
    private final AccountRepository accountRepository;

    @PostMapping
    public Response addAccount(@RequestBody @Valid AccountDto accountDto) throws Exception{
        Account account = accountMapper.AccountDto2Account(accountDto);
        Address address = addressMapper.AddressDto2Address(accountDto.getAddressDto());
        account.setAddress(address);
        Customer customer = customerRepository.findById(accountDto.getCustomerId())
                .orElseThrow(() -> new DataNotFoundException("Customer not found"));
        account.setCustomer(customer);

        Account saveAccount = accountRepository.save(account);
        accountService.updateAccountRedis(saveAccount);

        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "create account successfully",
                saveAccount
                );
    }

    @GetMapping
    public Response getAllAccount() {
        long startTime = System.currentTimeMillis();
        Response response = new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all accounts successfully",
                accountService.findAll()
        );
        long endTime = System.currentTimeMillis();
        log.info("getAllAccount() executed in {} ms", (endTime - startTime));
        return response;
    }

    @GetMapping("/getALl/redis")
    public Response getAllAccountRedis() {
        long startTime = System.currentTimeMillis();
        Response response = new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get all accounts redis successfully",
                accountService.getAllAccountRedis()
        );
        long endTime = System.currentTimeMillis();
        log.info("getAllAccountRedis() executed in {} ms", (endTime - startTime));
        return response;
    }

    @GetMapping("/{id}")
    public Response getAccountById(@PathVariable int id) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get account by id successfully",
                accountService.findById(id).orElseThrow(()-> new DataNotFoundException("Account not found"))
        );
    }

    @GetMapping("/cus/{customerId}")
    public Response getAccountByCustomerId(@PathVariable int customerId) throws DataNotFoundException {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "Get account by customer id successfully",
                accountService.findAccountByCustomerId(customerId).orElseThrow(()-> new DataNotFoundException("Account not found"))
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

    @PutMapping("/updateStatus/{accountNumber}")
    public Response updateAccount(@PathVariable String accountNumber ,@RequestBody AccountStatus accountNewStatus) throws Exception {
        accountService.changeAccountStatus(accountNumber, accountNewStatus);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "status account updated successfully",
               null
        );
    }

}
