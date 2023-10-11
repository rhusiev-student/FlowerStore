package flower.store;

import flower.store.items.Item;
import java.util.List;

public class FlowerBucket {
    private List<Item> bucketItems;

    public void add(Item item) {
        bucketItems.add(item);
    }

    public void add(List<Item> items) {
        this.bucketItems.addAll(items);
    }

    public double getPrice() {
        double price = 0;
        for (Item item : bucketItems) {
            price += item.getPrice();
        }
        return price;
    }
}
