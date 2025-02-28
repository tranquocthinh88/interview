package com.code.bank.api.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AccountDto {
    @NotBlank(message = "account number must be not blank")
    private String accountNumber;
    @NotNull(message = "balance must be not null")
    private double balance;
    private LocalDateTime openDate;
    @NotNull(message = "transaction limit must be not null")
    private double transactionLimit;
    private Integer customerId;
}
