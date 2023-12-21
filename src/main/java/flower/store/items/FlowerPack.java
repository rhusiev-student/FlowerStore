package flower.store.items;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

public class FlowerPack extends Item {
    private Flower flower;
    private double quantity;
    @Getter @Setter private String description;

    public FlowerPack(Flower flower, double quantity) {
        this.flower = flower;
        this.quantity = quantity;
        this.description = "A Flower Pack";
    }

    public FlowerPack(Flower flower, double quantity, String description) {
        this.flower = flower;
        this.quantity = quantity;
        this.description = description;
    }

    public double getPrice() { return flower.getPrice() * quantity; }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FlowerPack)) {
            return false;
        }
        FlowerPack pack = (FlowerPack) obj;
        return pack.flower.equals(flower) && pack.quantity == quantity
            && pack.description.equals(description);
    }

    public static FlowerPack fromJson(Map<String, Object> json) {
        return new FlowerPack(
            Flower.fromJson((Map<String, Object>) json.get("flower")),
            (int) json.get("quantity"), (String) json.get("description"));
    }

    public Map<String, Object> toJson() {
        return Map.of("quantity", quantity, "flower", flower.toJson(),
                      "description", description);
    }
}
