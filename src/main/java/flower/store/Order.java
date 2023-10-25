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

    public Order(List<Item> items) { this.items = items; }

    public Order() { this.items = new ArrayList<>(); }

    public void addItem(Item item) { items.add(item); }

    public void removeItem(Item item) { items.remove(item); }

    public void removeItemById(int id) { items.remove(id); }

    public List<Item> getItems() { return items; }

    public Map<String, Object> toJson() {
        List<Map<String, Object>> items = new ArrayList<>();
        for (Item item : this.items) {
            items.add(item.toJson());
        }
        return Map.of("items", items);
    }

    public static Order fromJson(Map<String, Object> json) {
        Order order = new Order();
        List<Map<String, Object>> items =
            (List<Map<String, Object>>)json.get("items");
        for (Map<String, Object> item : items) {
            if (item.containsKey("quantity")) {
                order.addItem(FlowerPack.fromJson(item));
            } else if (item.containsKey("items")) {
                order.addItem(FlowerBucket.fromJson(item));
            } else {
                order.addItem(Flower.fromJson(item));
            }
        }
        return order;
    }

    public void setPaymentStrategy(Map<String, Object> json) {
        paymentStrategy = PaymentStrategy.fromJson(json);
    }

    public int getPrice() {
        int price = 0;
        for (Item item : items) {
            price += item.getPrice();
        }
        return price;
    }

    public void pay() { paymentStrategy.pay(getPrice()); }

    public void setDeliveryStrategy(Map<String, Object> json) {
        deliveryStrategy = DeliveryStrategy.fromJson(json);
    }

    public void deliver() { deliveryStrategy.deliver(items); }
}
