package flower.store.items;

import java.util.Map;

public class FlowerPack implements Item {
    private Flower flower;
    private double quantity;

    public FlowerPack(Flower flower, double quantity) {
        this.flower = flower;
        this.quantity = quantity;
    }

    public double getPrice() { return flower.getPrice() * quantity; }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FlowerPack)) {
            return false;
        }
        FlowerPack pack = (FlowerPack)obj;
        return pack.flower.equals(flower) && pack.quantity == quantity;
    }

    public static FlowerPack fromJson(Map<String, Object> json) {
        return new FlowerPack(Flower.fromJson(json),
                              (double)json.get("quantity"));
    }

    public Map<String, Object> toJson() {
        return Map.of("quantity", quantity, "flower", flower.toJson());
    }
}
