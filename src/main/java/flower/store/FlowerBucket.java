package flower.store;

import java.util.List;

public class FlowerBucket {
    private List<Item> items;

    public double price() {
        double price = 0;
        for (Item item : items) {
            price += item.getPrice();
        }
        return price;
    }
}
