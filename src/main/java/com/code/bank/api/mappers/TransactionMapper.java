package com.code.bank.api.mappers;

import com.code.bank.api.dtos.requests.TransactionDto;
import com.code.bank.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "transactionType", target = "transactionType")
    Transaction Transaction2DtoTransaction(TransactionDto transactionDto);
}
