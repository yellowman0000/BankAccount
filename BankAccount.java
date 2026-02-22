//BankAccount
public class BankAccount implements IBankAccount {
    protected double balance; // Primitive
    private Customer owner;   // Reference
    private int priority;

    public BankAccount(Customer owner, double initialBalance, int priority) {
        this.owner = owner;
        this.balance = initialBalance;
        this.priority = priority;
    }


    // Show method stack nested loop
    @Override
    public void deposit(double amount) {
        if (validate(amount)) { 
            updateBalance(amount); 
        }
    }

    private boolean validate(double amount) {
        return amount > 0;
    }

    private void updateBalance(double amount) {
        this.balance += amount;
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public int getPriority() {
        return priority;
    }
    
    public String getOwnerName() {
        return owner.getName();
    }
}