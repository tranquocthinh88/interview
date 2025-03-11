package com.code.bank.api.mappers;

import com.code.bank.api.dtos.requests.AddressDto;
import com.code.bank.models.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address AddressDto2Address(AddressDto addressDto);
}
