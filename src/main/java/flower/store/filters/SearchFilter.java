package flower.store.filters;

import flower.store.items.Item;

public interface SearchFilter {
    boolean matches(Item item);
}
