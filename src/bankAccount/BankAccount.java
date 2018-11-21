package bankAccount;

import java.text.DecimalFormat;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Example of a simple thread safe bank account application
 *
 * @author Alex Kelly
 */
public class BankAccount {

    private String accountHolderName, accountNumber;
    private double balance;

    private Lock lock = new ReentrantLock();

    //format double monetary amounts
    public DecimalFormat money = new DecimalFormat("£0.00");

    /**
     * CONSTRUCTOR
     *
     * @param accountNumber this accounts unique account number
     * @param balance the initial balance
     */
    BankAccount(String accountHolderName, String accountNumber, double balance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Method for withdrawing money from this account
     *
     * @param amount the amount to be withdrawn
     * @return boolean value indicating whether withdrawal was successful
     */
    private boolean withdraw(double amount) {
        if ((balance - amount) < 0) {
            throw new IllegalArgumentException("maximum withdrawal amount for " + accountHolderName + " is " +
            money.format(balance) + " please try again");
        }

        if (lock.tryLock()) {
            try {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                balance -= amount;
                System.out.println(accountHolderName + " withdrew: " + money.format(amount) + " for " + Thread.currentThread().getName());
                return true;
            } finally {
                lock.unlock();
            }
        }

        return false;
    }

    /**
     * Method for depositing money into this account
     *
     * @param amount the amount to be deposited
     * @return boolean value indicating whether deposit was successful
     */
    private boolean deposit(double amount) {
        if (lock.tryLock()) {
            try {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                balance += amount;
                System.out.println(money.format(amount) + " deposited into " + accountHolderName + "'s account for "
                                    + Thread.currentThread().getName());
                return true;
            } finally {
                lock.unlock();
            }
        }

        return false;
    }

    /**
     * Method for transferring money between accounts
     *
     * @param destinationAccount where money is transferred to
     * @param amount the amount being transferred
     * @return boolean value indicating whether transaction was successful
     */
    public boolean transfer(BankAccount destinationAccount, double amount) {
        if (withdraw(amount)) {
            //deposit unsuccessful, refund money back into the account
            if (destinationAccount.deposit(amount)) {
                return true;
            }
            else {
                System.out.println(accountHolderName + "'s account busy, refunding money" +
                        " for " + Thread.currentThread().getName());
            }
        }

        return false;
    }
}
