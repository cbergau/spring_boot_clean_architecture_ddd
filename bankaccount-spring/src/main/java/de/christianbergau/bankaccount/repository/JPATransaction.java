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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private double amount;
    private String fromIban;
    private String toIban;
}
