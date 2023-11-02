package flower.store.decorators;

import flower.store.items.Item;
import lombok.Getter;
import lombok.Setter;

public class PaperDecorator extends AbstractDecorator {
    private Item item;
    @Getter @Setter private String description;

    public PaperDecorator(Item item) {
        this.item = item;
        this.description = "A paper wrapper";
    }

    public PaperDecorator(Item item, String description) {
        this.item = item;
        this.description = description;
    }

    @Override
    public double getPrice() {
        return item.getPrice() + 13;
    }
}
