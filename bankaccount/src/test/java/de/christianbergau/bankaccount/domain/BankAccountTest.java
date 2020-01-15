package de.christianbergau.bankaccount.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount();
    }

    @Test
    void testAccountIsInitiallyEmpty() {
        assertEquals(0.00, bankAccount.getBalance());
    }

    @Test
    void testTransferMoneyOnAnAccountShouldIncreaseItsBalance() {
        bankAccount.transfer(10.00);
        assertEquals(10.00, bankAccount.getBalance());

        bankAccount.transfer(5.00);
        assertEquals(15.00, bankAccount.getBalance());
    }

    @Test
    void testWithdrawMoneyDecreasesItsBalance() {
        bankAccount.transfer(100.00);
        bankAccount.withdraw(30.00);
        assertEquals(70.00, bankAccount.getBalance());

        bankAccount.transfer(100.00);
        assertEquals(170.00, bankAccount.getBalance());
    }
}
