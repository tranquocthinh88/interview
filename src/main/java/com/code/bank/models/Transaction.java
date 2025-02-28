package com.code.bank.models;

import com.code.bank.models.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "t_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    private String id;
    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(name = "amount")
    private double amount;
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    @Column(name = "fee")
    private double fee;
    @Column(name = "location")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY) // tối ưu dữ liệu, khi cần mới lấy
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
