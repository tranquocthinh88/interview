package com.code.bank.api.dtos.responses.transaction;

import com.code.bank.models.Transaction;
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
public class TransactionStatisticalResponse implements Serializable {
    private long count;
    private List<Transaction> transactions;

}
