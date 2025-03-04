package com.code.bank.api.dtos.requests;

import com.code.bank.models.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class TransactionDto {
    @NotNull(message = "transaction type must be not null")
    private TransactionType transactionType;
    @NotNull(message = "amount must be not null")
    private double amount;
    private LocalDateTime transactionDate;
    @NotNull(message = "location must be not null")
    private String location;
    private String receiverAccountNumber;
}
