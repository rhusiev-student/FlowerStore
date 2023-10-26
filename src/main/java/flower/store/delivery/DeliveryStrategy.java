package flower.store.delivery;

import flower.store.items.Item;
import java.util.List;
import java.util.Map;

public interface DeliveryStrategy {
    public void deliver(List<Item> items);

    public void setAddress(String address);

    public String getAddress();

    public static DeliveryStrategy fromJson(Map<String, Object> json) {
        if (!json.containsKey("type")) {
            throw new IllegalArgumentException("No type in json");
        }
        String type = (String)json.get("type");
        if (!json.containsKey("address")) {
            throw new IllegalArgumentException("No address in json");
        }
        if (type.equals("dhl")) {
            return new DHLDeliveryStrategy((String)json.get("address"));
        } else if (type.equals("post")) {
            return new PostDeliveryStrategy((String)json.get("address"));
        } else {
            throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
