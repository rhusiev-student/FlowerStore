package flower.store;

import flower.store.items.Item;

public class FlowerPack implements Item {
    private Flower flower;
    private double quantity;

    public FlowerPack(Flower flower, double quantity) {
        this.flower = flower;
        this.quantity = quantity;
    }

    public double getPrice() {
        return flower.getPrice() * quantity;
    }
}
