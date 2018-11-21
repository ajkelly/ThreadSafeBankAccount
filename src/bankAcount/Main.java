package bankAcount;

/**
 * @author Alex Kelly
 */
public class Main {

    /**
     * MAIN METHOD
     */
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount("Alex","123-456", 500.00);
        BankAccount account2 = new BankAccount("Katja", "654-321", 250.00);

        new Thread(new Transfer(account1, account2, 100.00), "rent").start();
        new Thread(new Transfer(account2, account1, 55.25), "gym membership").start();
    }
}
