package flower.store.payment;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    private int amount;

    public int getAmount() { return amount; }

    public CreditCardPaymentStrategy(int amount) { this.amount = amount; }

    public void pay(int amount) {
        if (this.amount == amount) {
            System.out.println("Paid " + amount + " with credit card");
        } else {
            System.out.println("Not enough money on credit card");
        }
    }
}
