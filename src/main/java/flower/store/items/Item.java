package flower.store.items;

import java.util.Map;

public interface Item {
    double getPrice();

    boolean equals(Object item);

    Map<String, Object> toJson();

    public static Item fromJson(Map<String, Object> json) {
        if (json.containsKey("quantity")) {
            return FlowerPack.fromJson(json);
        } else if (json.containsKey("items")) {
            return FlowerBucket.fromJson(json);
        } else {
            return Flower.fromJson(json);
        }
    }
}
