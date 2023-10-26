package flower.store.payment;

import java.util.Map;

public interface PaymentStrategy {
    public void pay(int amount);

    public int getAmount();

    public static PaymentStrategy fromJson(Map<String, Object> json) {
        if (!json.containsKey("amount")) {
            throw new IllegalArgumentException("No amount in json");
        }
        if (!json.containsKey("type")) {
            throw new IllegalArgumentException("No type in json");
        }
        int amount;
        try {
            amount = (int)json.get("amount");
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Amount is not an integer");
        }
        String type = (String)json.get("type");
        if (type.equals("credit_card")) {
            return new CreditCardPaymentStrategy(amount);
        } else if (type.equals("paypal")) {
            return new PayPalPaymentStrategy(amount);
        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
