//Interface BankAccount
public interface IBankAccount {
    void deposit(double amount);
    boolean withdraw(double amount);
    double getBalance();
    int getPriority();
    String getOwnerName();
}
