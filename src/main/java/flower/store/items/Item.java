package flower.store.items;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@MappedSuperclass
public abstract class Item {
    @Id
    private int id;
    @Getter private double price;
    @Getter @Setter private String description;

    public boolean equals(Object item) {
        return false;
    }

    public Map<String, Object> toJson() {
        return null;
    }

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
