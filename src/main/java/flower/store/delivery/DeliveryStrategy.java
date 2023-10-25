package flower.store.delivery;

import java.util.List;
import java.util.Map;

import flower.store.items.Item;

public interface DeliveryStrategy {
    public void deliver(List<Item> items);

    public void setAddress(String address);

    public String getAddress();

    public static DeliveryStrategy fromJson(Map<String, Object> json) {
        if (!json.containsKey("type")) {
            throw new IllegalArgumentException("No type in json");
        }
        if (!json.containsKey("address")) {
            throw new IllegalArgumentException("No address in json");
        }
        String type = (String)json.get("type");
        String address = (String)json.get("address");
        if (type.equals("post")) {
            return new PostDeliveryStrategy(address);
        } else if (type.equals("dhl")) {
            return new DHLDeliveryStrategy(address);
        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
