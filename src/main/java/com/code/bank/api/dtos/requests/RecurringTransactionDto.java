package com.code.bank.api.dtos.requests;

import com.code.bank.models.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecurringTransactionDto {
    @NotNull(message = "account must be not null")
    private Integer accountId;
    private TransactionType transactionType;
    @NotNull(message = "amount must be not null")
    private double amount;
    private String receiverAccount;
    private String description;
}
