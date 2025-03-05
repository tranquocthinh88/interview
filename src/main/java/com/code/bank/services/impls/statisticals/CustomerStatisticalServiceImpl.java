package com.code.bank.services.impls.statisticals;

import com.code.bank.models.Address;
import com.code.bank.models.Customer;
import com.code.bank.repositories.CustomerRepository;
import com.code.bank.services.interfaces.statisticals.CustomerStatisticalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerStatisticalServiceImpl implements CustomerStatisticalService {

    private final CustomerRepository customerRepository;

    public CustomerStatisticalServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomerCountByLocation(String location) {
        return customerRepository.findCustomersByLocation(location);
    }
}
