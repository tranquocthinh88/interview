package com.code.bank.api.mappers;

import com.code.bank.api.dtos.requests.AccountDto;
import com.code.bank.models.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "customer", ignore = true)
    Account AccountDto2Account(AccountDto accountDto);

}