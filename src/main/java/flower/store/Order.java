package flower.store;

import flower.store.delivery.DeliveryStrategy;
import flower.store.items.Flower;
import flower.store.items.FlowerBucket;
import flower.store.items.FlowerPack;
import flower.store.items.Item;
import flower.store.payment.PaymentStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Order {
    private List<Item> items;
    private PaymentStrategy paymentStrategy;
    private DeliveryStrategy deliveryStrategy;
    private List<AppUser> users;

    public Order(List<Item> items) {
        this.items = items;
        this.users = new ArrayList<>();
    }

    public Order() {
        this.items = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void removeItemById(int id) {
        items.remove(id);
    }

    public List<Item> getItems() {
        return items;
    }

    public Map<String, Object> toJson() {
        List<Map<String, Object>> items = new ArrayList<>();
        for (Item item : this.items) {
            items.add(item.toJson());
        }
        String paymentStrategyName = "unknown";
        String deliveryStrategyName = "unknown";
        Integer paymentStrategyAmount = 0;
        String deliveryStrategyAddress = "";
        if (paymentStrategy == null) {
            paymentStrategyName = "undefined";
        } else {
            if (paymentStrategy.getClass().getName().equals(
                    "flower.store.payment.CreditCardPaymentStrategy")) {
                paymentStrategyName = "credit_card";
                paymentStrategyAmount = paymentStrategy.getAmount();
            } else if (paymentStrategy.getClass().getName().equals(
                           "flower.store.payment.PayPalPaymentStrategy")) {
                paymentStrategyName = "paypal";
                paymentStrategyAmount = paymentStrategy.getAmount();
            }
        }
        if (deliveryStrategy == null) {
            deliveryStrategyName = "undefined";
        } else {
            if (deliveryStrategy.getClass().getName().equals(
                    "flower.store.delivery.DhlDeliveryStrategy")) {
                deliveryStrategyName = "dhl";
                deliveryStrategyAddress = deliveryStrategy.getAddress();
            } else if (deliveryStrategy.getClass().getName().equals(
                           "flower.store.delivery.PostDeliveryStrategy")) {
                deliveryStrategyName = "post";
                deliveryStrategyAddress = deliveryStrategy.getAddress();
            }
        }
        return Map.of("items", items, "payment_strategy",
                      Map.of("type", paymentStrategyName, "amount",
                             paymentStrategyAmount),
                      "delivery_strategy",
                      Map.of("type", deliveryStrategyName, "address",
                             deliveryStrategyAddress));
    }

    public static Order fromJson(Map<String, Object> json) {
        Order order = new Order();
        List<Map<String, Object>> items =
            (List<Map<String, Object>>) json.get("items");
        for (Map<String, Object> item : items) {
            if (item.containsKey("quantity")) {
                order.addItem(FlowerPack.fromJson(item));
            } else if (item.containsKey("items")) {
                order.addItem(FlowerBucket.fromJson(item));
            } else if (item.containsKey("payment_strategy")) {
                order.setPaymentStrategy(item);
            } else if (item.containsKey("delivery_strategy")) {
                order.setDeliveryStrategy(item);
            } else {
                order.addItem(Flower.fromJson(item));
            }
        }
        return order;
    }

    public void setPaymentStrategy(Map<String, Object> json) {
        this.paymentStrategy = PaymentStrategy.fromJson(json);
    }

    public int getPrice() {
        int price = 0;
        for (Item item : items) {
            price += item.getPrice();
        }
        return price;
    }

    public void pay() {
        paymentStrategy.pay(getPrice());
    }

    public void setDeliveryStrategy(Map<String, Object> json) {
        this.deliveryStrategy = DeliveryStrategy.fromJson(json);
    }

    public void deliver() {
        deliveryStrategy.deliver(items);
    }

    public void addUser(AppUser user) {
        users.add(user);
    }

    public void removeUser(AppUser user) {
        users.remove(user);
    }

    public void notifyUsers() {
        for (AppUser user : users) {
            user.update("Your order is ready!");
        }
    }

    public void order() {
        pay();
        deliver();
        notifyUsers();
    }
}
