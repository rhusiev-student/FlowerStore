package flower.store;

import flower.store.filters.SearchFilter;
import flower.store.items.Flower;
import flower.store.items.FlowerBucket;
import flower.store.items.FlowerPack;
import flower.store.items.Item;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class FlowerService {
    @Getter private List<Item> items;

    public FlowerService() {
        items = new ArrayList<>();
    }

    public void addFlower(Flower flower) {
        items.add(flower);
    }

    public void addFlowerPack(FlowerPack flowerPack) {
        items.add(flowerPack);
    }

    public void addFlowerBucket(FlowerBucket flowerBucket) {
        items.add(flowerBucket);
    }

    public void removeFlower(Flower flower) {
        for (Item item : items) {
            if (flower.equals(item)) {
                items.remove(item);
                break;
            }
        }
    }

    public void removeFlowerPack(FlowerPack flowerPack) {
        for (Item item : items) {
            if (flowerPack.equals(item)) {
                items.remove(item);
                break;
            }
        }
    }

    public void removeFlowerBucket(FlowerBucket flowerBucket) {
        for (Item item : items) {
            if (flowerBucket.equals(item)) {
                items.remove(item);
                break;
            }
        }
    }

    public void removeItemById(int id) {
        items.remove(id);
    }

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
