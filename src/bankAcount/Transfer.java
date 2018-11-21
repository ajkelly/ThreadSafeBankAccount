package bankAcount;

/**
 * @author Alex Kelly
 */
public class Transfer implements Runnable {

    private BankAccount sourceAccount, destinationAccount;
    private double amount;

    /**
     * CONSTRUCTOR
     *
     * @param sourceAccount where money is withdrawn from
     * @param destinationAccount where money is deposited to
     * @param amount the amount being withdrawn/deposited
     */
    Transfer(BankAccount sourceAccount, BankAccount destinationAccount, double amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public void run() {
        while (!sourceAccount.transfer(destinationAccount, amount))
            continue;
        System.out.println(Thread.currentThread().getName() + " transaction completed!");

        //print balance following transactions
        System.out.println(sourceAccount.getAccountHolderName() + "'s current balance = " +
                            sourceAccount.getBalance());
        System.out.println(destinationAccount.getAccountHolderName() + "'s current balance = " +
                destinationAccount.getBalance());
    }

}
