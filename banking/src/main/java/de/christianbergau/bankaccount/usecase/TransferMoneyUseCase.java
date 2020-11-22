package de.christianbergau.bankaccount.usecase;

public interface TransferMoneyUseCase {
    public void execute(TransferMoneyRequest request);
    public void executeAsync(TransferMoneyRequest request);
}
