package de.christianbergau.bankaccount.repository;

public interface DtoMapper<T, U> {
    public U toDto(T t);
    public T toDomainEntity(U u);
}
