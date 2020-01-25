package de.christianbergau.bankaccount.repository;

public interface JPAMapper<T, U> {
    public U toJPAEntity(T t);
    public T toDomainEntity(U u);
}
