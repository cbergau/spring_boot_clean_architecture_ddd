package de.christianbergau.bankaccount.repository;

import org.springframework.data.repository.CrudRepository;

public interface JPATransactionRepository extends CrudRepository<JPATransaction, Long> {
}
