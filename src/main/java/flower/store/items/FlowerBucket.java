package flower.store.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import flower.store.filters.SearchFilter;
import lombok.Getter;
import lombok.Setter;

public class FlowerBucket extends Item {
    private List<Item> bucketItems;
    @Getter @Setter private String description;

    public FlowerBucket() {
        bucketItems = new ArrayList<>();
        description = "A Flower Bucket";
    }

    public FlowerBucket(String description) {
        bucketItems = new ArrayList<>();
        this.description = description;
    }

    public void add(Item item) {
        bucketItems.add(item);
    }

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
        FlowerBucket bucket = (FlowerBucket) obj;
        return bucket.bucketItems.equals(bucketItems)
            && bucket.description.equals(description);
    }

    public Map<String, Object> toJson() {
        List<Map<String, Object>> items = new ArrayList<>();
        for (Item item : bucketItems) {
            items.add(item.toJson());
        }
        return Map.of("items", items, "description", description);
    }

    public static FlowerBucket fromJson(Map<String, Object> json) {
        FlowerBucket bucket = new FlowerBucket();
        List<Map<String, Object>> items =
            (List<Map<String, Object>>) json.get("items");
        String description = (String) json.get("description");
        bucket.setDescription(description);
        for (Map<String, Object> item : items) {
            if (item.containsKey("quantity")) {
                bucket.add(FlowerPack.fromJson(item));
            } else {
                bucket.add(Flower.fromJson(item));
            }
        }
        return bucket;
    }

    public Item searchFlower(SearchFilter filter) {
        for (Item item : bucketItems) {
            if (filter.matches(item)) {
                return item;
            }
        }
        return null;
    }
}
