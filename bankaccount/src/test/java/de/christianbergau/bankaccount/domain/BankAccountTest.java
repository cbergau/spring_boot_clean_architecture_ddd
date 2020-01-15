package de.christianbergau.bankaccount.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount(IBAN.of("DE89 3704 0044 0532 0130 00"));
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

    @Test
    void testTwoAccoutsWithSameIBANAreEqual() {
        IBAN iban = IBAN.of("DE89 3704 0044 0532 0130 00");
        BankAccount bankAccount1 = new BankAccount(iban);
        BankAccount bankAccount2 = new BankAccount(iban);

        assertEquals(bankAccount1, bankAccount2);
        assertEquals(bankAccount1.hashCode(), bankAccount2.hashCode());
    }

    @Test
    void testTwoAccoutsWithDifferentIBANAreEqual() {
        BankAccount bankAccount1 = new BankAccount(IBAN.of("DE89 3704 0044 0532 0130 00"));
        BankAccount bankAccount2 = new BankAccount(IBAN.of("DE89 3704 0044 0532 1111 22"));

        assertNotEquals(bankAccount1, bankAccount2);
        assertNotEquals(bankAccount1.hashCode(), bankAccount2.hashCode());
    }

    @Test
    void testBankAccountIsNotEqualsNull() {
        assertNotEquals(new BankAccount(IBAN.of("DE89 3704 0044 0532 0130 00")), null);
    }

    @Test
    void testBankAccountIsNotEqualToAnIban() {
        IBAN iban = IBAN.of("DE89 3704 0044 0532 0130 00");
        assertNotEquals(new BankAccount(iban), iban);
    }
}
