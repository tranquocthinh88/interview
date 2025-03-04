package com.code.bank.api.mappers;

import com.code.bank.api.dtos.requests.CustomerDto;
import com.code.bank.models.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(source = "gender", target = "gender")
    Customer CustomerDto2Customer(CustomerDto customerDto);
}
