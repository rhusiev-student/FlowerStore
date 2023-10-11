package flower.store;

import flower.store.filters.SearchFilter;
import flower.store.items.Item;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class Store {
    @Getter
    private List<Item> items;

    public Store() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
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
