package flower.store.delivery;

import flower.store.items.Item;
import java.util.List;

public class PostDeliveryStrategy implements DeliveryStrategy {
    private String address;

    public PostDeliveryStrategy(String address) {
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void deliver(List<Item> items) {
        System.out.println("Delivered" + items + " with post to " + address);
    }
}
