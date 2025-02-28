package com.code.bank.services.impls;

import com.code.bank.models.Customer;
import com.code.bank.services.interfaces.CustomerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer, Integer> implements CustomerService {
    public CustomerServiceImpl(JpaRepository<Customer, Integer> repository) {
        super(repository, Customer.class);
    }
}
