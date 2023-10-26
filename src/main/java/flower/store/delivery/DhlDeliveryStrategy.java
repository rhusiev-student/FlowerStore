package flower.store.delivery;

import flower.store.items.Item;
import java.util.List;

public class DhlDeliveryStrategy implements DeliveryStrategy {
    private String address;

    public DhlDeliveryStrategy(String address) {
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void deliver(List<Item> items) {
        System.out.println("Delivered" + items + " with dhl to " + address);
    }
}
