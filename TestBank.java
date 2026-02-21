import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestBank {
    private List<IBankAccount> accountQueue = new ArrayList<>();

    public static void main(String[] args) {
        TestBank engine = new TestBank();
        Customer owner = new Customer("User");
        
        BankAccount mainAcc = new BankAccount(owner, 1000.0, 1);
        SavingsAccount saveAcc = new SavingsAccount(owner, 500.0, 2, 0.05);

        double testPrimitive = 500.0;
        engine.modifyValues(testPrimitive, mainAcc);

        engine.runConcurrentTransactions(mainAcc);

        engine.accountQueue.add(saveAcc);
        engine.accountQueue.add(mainAcc);
        engine.scheduleTransactions();
    }

    public void modifyValues(double val, BankAccount acc) {
        val = val + 1000;
        acc.deposit(1000);
    }

    public void runConcurrentTransactions(BankAccount acc) {
        ExecutorService executor = Executors.newFixedThreadPool(3); 

        Runnable loginAndDeposit = () -> {
            for (int i = 0; i < 50; i++) {
                acc.deposit(10.0);
            }
        };

        executor.execute(loginAndDeposit);
        executor.execute(loginAndDeposit);
        executor.execute(loginAndDeposit);

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }

    public void scheduleTransactions() {
        accountQueue.sort(Comparator.comparingInt(IBankAccount::getPriority));
        for (IBankAccount acc : accountQueue) {
            System.out.println("Priority: " + acc.getPriority() + " | Balance: " + acc.getBalance());
        }
    }
}// TestBank
