package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;


/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    private static int CANNOT_WITHDRAW_AMOUNT = 1000;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder( "Mario","Rossi", 0);
        this.bankAccount = new StrictBankAccount(mRossi, 0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals((double)0, this.bankAccount.getBalance());
        assertEquals(mRossi, bankAccount.getAccountHolder());
        assertEquals(0.0, bankAccount.getTransactionsCount());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        this.bankAccount.deposit(mRossi.getUserID(),100);
        this.bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals((double)95, bankAccount.getBalance(), 0.1);
        assertEquals(0, bankAccount.getTransactionsCount());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            this.bankAccount.withdraw(mRossi.getUserID(), -10);
            Assertions.fail("cannot withdraw a negative amount");
        }catch(IllegalArgumentException e){
            assertEquals(0.0, this.bankAccount.getBalance());
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try{
            this.bankAccount.withdraw(mRossi.getUserID(), CANNOT_WITHDRAW_AMOUNT);
            Assertions.fail("cannot withdraw");
        }catch(IllegalArgumentException e){
            assertEquals(0.0, bankAccount.getBalance());
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
        }
    }
}
