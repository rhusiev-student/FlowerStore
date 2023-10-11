package flower.store;

import flower.store.filters.SearchFilter;
import flower.store.items.Item;
import java.util.ArrayList;
import java.util.List;

public class Store {
    public List<Item> items;

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
