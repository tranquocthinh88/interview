package com.code.bank.api.controllers;

import com.code.bank.api.dtos.requests.CustomerDto;
import com.code.bank.api.dtos.responses.Response;
import com.code.bank.api.dtos.responses.ResponseSuccess;
import com.code.bank.api.exceptions.DataNotFoundException;
import com.code.bank.api.mappers.CustomerMapper;
import com.code.bank.models.Customer;
import com.code.bank.services.interfaces.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping ("/api/v1/customers")
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

    @DeleteMapping("/{id}")
    public Response deleteCustomer(@PathVariable int id) throws DataNotFoundException {
        customerService.delete(id);
        return new ResponseSuccess<>(HttpStatus.NO_CONTENT.value(),
                "delete customer successfully" + id);
    }

    @PatchMapping("/{id}")
    public Response patchCustomer(@PathVariable int id, @RequestBody @Valid Map<String, ?> data) throws Exception {
        return new ResponseSuccess<>(
                HttpStatus.OK.value(),
                "address updated successfully",
                customerService.updatePatch(id, data)
        );
    }

    @PutMapping("/{id}")
    public Response updateCustomer(@PathVariable int id ,@RequestBody @Valid CustomerDto customerDto) throws Exception {
        Customer customer = customerMapper.CustomerDto2Customer(customerDto);
        customer.setId(id);
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "address updated successfully",
                customerService.update(id, customer));
    }

    @GetMapping
    public Response getAllCustomers() {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
        "Get all customer successfully",
                customerService.findAll());
    }

    @GetMapping("/{id}")
    public Response getCustomerById(@PathVariable int id) throws DataNotFoundException {
        return new ResponseSuccess<>(HttpStatus.OK.value(),
                "Get customer by id successfully",
                customerService.findById(id).orElseThrow(() -> new DataNotFoundException("Customer not found")));
    }
}
