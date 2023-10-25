package flower.store;

import flower.store.filters.SearchFilter;
import flower.store.items.Flower;
import flower.store.items.FlowerBucket;
import flower.store.items.FlowerPack;
import flower.store.items.Item;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class Store {
    @Getter private List<Item> items;

    public Store() { items = new ArrayList<>(); }

    public void addFlower(Flower flower) { items.add(flower); }

    public void addFlowerPack(FlowerPack flowerPack) { items.add(flowerPack); }

    public void addFlowerBucket(FlowerBucket flowerBucket) {
        items.add(flowerBucket);
    }

    public void removeFlower(Flower flower) { items.remove(flower); }

    public void removeFlowerPack(FlowerPack flowerPack) {
        items.remove(flowerPack);
    }

    public void removeFlowerBucket(FlowerBucket flowerBucket) {
        items.remove(flowerBucket);
    }

    public void removeItemById(int id) { items.remove(id); }

    public List<Item> search(SearchFilter filter) {
        List<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (filter.matches(item)) {
                result.add(item);
            }
        }
        return result;
    }
}
