//TestBank
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestBank {
    // Made a queue to hold accounts, will be sorted by priority
    private List<IBankAccount> accountQueue = new ArrayList<>();
    
    public static void main(String[] args) {
        // Create system
        TestBank tester = new TestBank();

        // Create customers
        Customer c1 = new Customer("Project");
        Customer c2 = new Customer("Q");
        Customer c3 = new Customer("Chole");
        Customer c4 = new Customer("Atul");
        Customer c5 = new Customer("Gun");

        // Main and savings account
        BankAccount acc1 = new BankAccount(c1, 1000.0, 3); // Project
        SavingsAccount acc2 = new SavingsAccount(c2, 500.0, 1, 0.05); //Q (Highest priority)
        BankAccount acc3 = new BankAccount(c3, 1500.0, 5); // Chole (Lowest priority)
        BankAccount acc4 = new BankAccount(c4, 3000.0, 2); // Atul
        BankAccount acc5 = new BankAccount(c5, 500.0, 4);  // Gun

        // Demonstrate how primitive vs reference type
        System.out.println("=== Memory Test: Primitive vs Reference ===");
        double testPrimitive = 500.0;
        System.out.println("Before method: Primitive = " + testPrimitive + ", Balance = " + acc1.getBalance());

        tester.modifyValues(testPrimitive, acc1);
        System.out.println("After method (Stack check): Primitive = " + testPrimitive); 
        System.out.println("After method (Heap check): Balance = " + acc1.getBalance());
        System.out.println("-------------------------------------------\n");

        // Demonstrate Concurrency (Multiple strack)
        System.out.println("=== Concurrency Test for Q's Account ===");
        System.out.println("Starting threads to deposit money...");
        tester.runConcurrentTransactions(acc2);

        // Add accounts to the queue and sort
        tester.accountQueue.add(acc1);
        tester.accountQueue.add(acc2);
        tester.accountQueue.add(acc3);
        tester.accountQueue.add(acc4);
        tester.accountQueue.add(acc5);
        System.out.println("\n=== Final Priority Scheduling (All Customers) ===");
        tester.scheduleTransactions();

        // Demonstrate Stack Overflow (The Crash Test)
        // ------ Uncomment the code below to see the stack overflow in action (Warning: It will crash the program) ----------
        /* System.out.println("\n=== Stack Overflow Test (Limit of Memory) ===");
        System.out.println("Wait for 3 seconds before crashing...");
        try {
            Thread.sleep(3000); // time to read the message before crashing (3 seconds)
        } catch (InterruptedException e) {}

        acc2.triggerOverflow(); */
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
                // Print Thred name
                System.out.println(Thread.currentThread().getName() + " deposited 10. Remaining Loop: " + (49-i));
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
            System.out.println("Priority: " + acc.getPriority() + " | Owner: " + acc.getOwnerName() +" | Balance: " + acc.getBalance());
        }
    }
}