package de.christianbergau.bankaccount.repository;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Builder
@Getter
@Entity
@Table(name = "Transactions")
public class JPATransaction {
    @Id
    @GeneratedValue()
    private Long id;

    private String transactionNumber;
    private double amount;
    private String fromIban;
    private String toIban;
}
