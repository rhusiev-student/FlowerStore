package flower.store;

import flower.store.items.Item;
import java.util.List;

public class FlowerBucket {
    private List<Item> items;

    public void add(Item item) {
        items.add(item);
    }

    public void add(List<Item> items) {
        this.items.addAll(items);
    }

    public double getPrice() {
        double price = 0;
        for (Item item : items) {
            price += item.getPrice();
        }
        return price;
    }
}
