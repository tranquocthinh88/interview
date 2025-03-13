package com.code.bank.api.dtos.responses.customer;

import com.code.bank.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerStatisticalResponse implements Serializable {
    private long count;
    private List<Customer> customers;
}
