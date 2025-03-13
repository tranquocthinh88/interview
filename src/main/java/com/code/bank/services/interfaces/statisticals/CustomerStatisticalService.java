package com.code.bank.services.interfaces.statisticals;

import com.code.bank.api.dtos.responses.customer.CustomerStatisticalResponse;

public interface CustomerStatisticalService {
    CustomerStatisticalResponse getCustomerCountByLocation(String location);
}
