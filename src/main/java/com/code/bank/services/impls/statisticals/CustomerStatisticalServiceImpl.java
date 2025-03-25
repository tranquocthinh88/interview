package com.code.bank.services.impls.statisticals;

import com.code.bank.api.dtos.responses.customer.CustomerStatisticalResponse;
import com.code.bank.repositories.CustomerRepository;
import com.code.bank.services.interfaces.statisticals.CustomerStatisticalService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerStatisticalServiceImpl implements CustomerStatisticalService {

    private final CustomerRepository customerRepository;

    public CustomerStatisticalServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public CustomerStatisticalResponse getCustomerCountByLocation(String location, Pageable pageable) {
        CustomerStatisticalResponse response = new CustomerStatisticalResponse();
        response.setCount(customerRepository.countCustomersByLocation(location));
        response.setCustomers(customerRepository.findCustomersByLocation(location, pageable).getContent());
        return response;
    }
}
