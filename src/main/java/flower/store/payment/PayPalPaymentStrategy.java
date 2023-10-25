package flower.store.payment;

public class PayPalPaymentStrategy implements PaymentStrategy {
    private int amount;

    public PayPalPaymentStrategy(int amount) { this.amount = amount; }

    public void pay(int amount) {
        if (this.amount == amount) {
            System.out.println("Paid " + amount + " with paypal");
        } else {
            System.out.println("Not enough money on paypal");
        }
    }
}
