package flower.store.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlowerBucket implements Item {
    private List<Item> bucketItems;

    public FlowerBucket() { bucketItems = new ArrayList<>(); }

    public void add(Item item) { bucketItems.add(item); }

    public void add(List<Item> items) { this.bucketItems.addAll(items); }

    public double getPrice() {
        double price = 0;
        for (Item item : bucketItems) {
            price += item.getPrice();
        }
        return price;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FlowerBucket)) {
            return false;
        }
        FlowerBucket bucket = (FlowerBucket)obj;
        return bucket.bucketItems.equals(bucketItems);
    }

    public Map<String, Object> toJson() {
        List<Map<String, Object>> items = new ArrayList<>();
        for (Item item : bucketItems) {
            items.add(item.toJson());
        }
        return Map.of("items", items);
    }

    public static FlowerBucket fromJson(Map<String, Object> json) {
        FlowerBucket bucket = new FlowerBucket();
        List<Map<String, Object>> items =
            (List<Map<String, Object>>)json.get("items");
        for (Map<String, Object> item : items) {
            if (item.containsKey("quantity")) {
                bucket.add(FlowerPack.fromJson(item));
            } else {
                bucket.add(Flower.fromJson(item));
            }
        }
        return bucket;
    }
}
