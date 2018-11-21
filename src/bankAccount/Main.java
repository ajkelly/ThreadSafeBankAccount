package bankAccount;

/**
 * @author Alex Kelly
 */
public class Main {

    /**
     * MAIN METHOD
     */
    public static void main(String[] args) {
        BankAccount accountAlex = new BankAccount("Alex","123-456", 500.00);
        BankAccount accountKatja = new BankAccount("Katja", "654-321", 800.00);

        new Thread(new Transfer(accountAlex, accountKatja, 100.00), "rent").start();
        new Thread(new Transfer(accountKatja, accountAlex, 50), "gym membership").start();
    }
}
