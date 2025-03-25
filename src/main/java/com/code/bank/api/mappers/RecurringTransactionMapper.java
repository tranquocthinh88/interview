package com.code.bank.api.mappers;

import com.code.bank.api.dtos.requests.RecurringTransactionDto;
import com.code.bank.models.Account;
import com.code.bank.models.RecurringTransaction;
import com.code.bank.repositories.AccountRepository;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface RecurringTransactionMapper {

    @Mapping(target = "account", ignore = true)
    RecurringTransaction recurringTransactionDtoToRecurringTransaction(RecurringTransactionDto dto,
                                                                       @Context Account account);

    @AfterMapping
    default void setDefault(@MappingTarget RecurringTransaction recurringTransaction) {
            recurringTransaction.setStartDate(LocalDateTime.now());
            recurringTransaction.setActive(true);

    }
}
