package com.code.bank.api.dtos.responses.account;

import com.code.bank.models.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountStatisticalResponse implements Serializable {
    private long count;
    private List<Account> accounts;
}
