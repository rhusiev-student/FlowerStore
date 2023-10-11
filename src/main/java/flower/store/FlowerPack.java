package flower.store;

import flower.store.items.Item;

public class FlowerPack implements Item {
    private Flower flower;
    private int amount;

    public double getPrice() {
        return flower.getPrice() * amount;
    }
}
