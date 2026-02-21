import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestBank {
    // Made a queue to hold accounts, will be sorted by priority
    private List<IBankAccount> accountQueue = new ArrayList<>();
    
    public static void main(String[] args) {
        // Create system and customer
        TestBank engine = new TestBank();
        Customer owner = new Customer("User");

        // Main and savings account
        BankAccount mainAcc = new BankAccount(owner, 1000.0, 1);
        SavingsAccount saveAcc = new SavingsAccount(owner, 500.0, 2, 0.05);

        // Example to show primitive vs reference type
        double testPrimitive = 500.0;
        engine.modifyValues(testPrimitive, mainAcc);

        engine.runConcurrentTransactions(mainAcc);

        // Add accounts to the queue and sort
        engine.accountQueue.add(saveAcc);
        engine.accountQueue.add(mainAcc);
        engine.scheduleTransactions();
    }

    
    public void modifyValues(double val, BankAccount acc) {
        val = val + 1000; // Primitive values don't actually change
        acc.deposit(1000); // Update the actual variable
    }

    // Uses multiple threads to deposit money at the same time
    public void runConcurrentTransactions(BankAccount acc) {
        ExecutorService executor = Executors.newFixedThreadPool(3); 

        Runnable loginAndDeposit = () -> {
            for (int i = 0; i < 50; i++) {
                acc.deposit(10.0);
            }
        };
        
        //Execute same tasks in 3 threads
        executor.execute(loginAndDeposit);
        executor.execute(loginAndDeposit);
        executor.execute(loginAndDeposit);

        // Shuts down the executor and wait for all tasks to complete
        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    // Sort accounts by priority, and prints balance
    public void scheduleTransactions() {
        accountQueue.sort(Comparator.comparingInt(IBankAccount::getPriority));
        for (IBankAccount acc : accountQueue) {
            System.out.println("Priority: " + acc.getPriority() + " | Balance: " + acc.getBalance());
        }
    }
}// TestBank
