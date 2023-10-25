package flower.store.delivery;

import java.util.List;

import flower.store.items.Item;

public class DHLDeliveryStrategy implements DeliveryStrategy {
    private String address;

    public DHLDeliveryStrategy(String address) { this.address = address; }

    public void setAddress(String address) { this.address = address; }

    public String getAddress() { return address; }

    public void deliver(List<Item> items) {
        System.out.println("Delivered" + items + " with dhl to " + address);
    }
}
