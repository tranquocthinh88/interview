package com.code.bank.api.controllers;

import com.code.bank.api.dtos.requests.CustomerDto;
import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.api.mappers.CustomerMapper;
import com.code.bank.models.Customer;
import com.code.bank.services.interfaces.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/v1/customers")
//@RequiredArgsConstructor
public class CustomerController {

    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerMapper customerMapper, CustomerService customerService) {
        this.customerMapper = customerMapper;
        this.customerService = customerService;
    }

    @PostMapping
    public Response addCustomer(@RequestBody @Valid CustomerDto customerDto) {
        Customer customer = customerMapper.CustomerDto2Customer(customerDto);
        return new ResponseSuccess<>(HttpStatus.OK.value(), "create customer successfully",
                customerService.save(customer));
    }
}
