// SavingsAccount
public class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(Customer owner, double initialBalance, int priority, double interestRate) {
        super(owner, initialBalance, priority);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interestAmount = getBalance() * interestRate;
        deposit(interestAmount);
        System.out.println("Interest applied: $" + interestAmount);
    }

    public void triggerOverflow() {
        System.out.println("Starting stack overflow demonstration...");
        recursiveCall(1);
    }
    
    private void recursiveCall(int depth) {
        System.out.println("Stack depth: " + depth);
        recursiveCall(depth + 1);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

}