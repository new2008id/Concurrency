public class CashDispenser {
    private int balance;
    private final Object monitor = new Object();

    public CashDispenser(int balance) {
        this.balance = balance;
    }

    public void withdraw(String name, int amount) {
        System.out.println(name + " went to the ATM");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (amount > balance) {
            System.out.println("There is not enough money in the ATM for " + name);
        } else {
            balance -= amount;
            System.out.println(name + " withdrawn " + amount + " rubles." + " There are: " + balance + " rubles left in the ATM");
        }

        synchronized (monitor) {
            System.out.println(name + " went to the ATM");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (amount > balance) {
                System.out.println("There is not enough money in the ATM for " + name);
            } else {
                balance -= amount;
                System.out.println(name + " withdrawn " + amount + " rubles." + " There are: " + balance + " rubles left in the ATM");
            }
        }
    }
}
