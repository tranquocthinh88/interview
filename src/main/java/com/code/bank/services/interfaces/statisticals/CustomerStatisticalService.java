package com.code.bank.services.interfaces.statisticals;

import com.code.bank.models.Address;
import com.code.bank.models.Customer;

import java.util.List;

public interface CustomerStatisticalService {
    List<Customer> getCustomerCountByLocation(String location);
}
