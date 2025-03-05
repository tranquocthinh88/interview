package com.code.bank.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "t_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int id;
    @Column(name = "account_number", unique = true)
    private String accountNumber;
    @Column(name = "balance")
    private double balance;
    @Column(name = "open_date")
    private LocalDateTime openDate;
    @Column(name = "transaction_limit")
    private double transactionLimit;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", unique = true)
    private Customer customer;
}
