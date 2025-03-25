package com.code.bank.services.interfaces.statisticals;

import com.code.bank.api.dtos.responses.customer.CustomerStatisticalResponse;
import org.springframework.data.domain.Pageable;

public interface CustomerStatisticalService {
    CustomerStatisticalResponse getCustomerCountByLocation(String location, Pageable pageable);
}
