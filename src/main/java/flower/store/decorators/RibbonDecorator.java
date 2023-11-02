package flower.store.decorators;

import flower.store.items.Item;
import lombok.Getter;
import lombok.Setter;

public class RibbonDecorator extends AbstractDecorator {
    private Item item;
    @Getter @Setter private String description;

    public RibbonDecorator(Item item) {
        this.item = item;
        this.description = "A ribbon";
    }

    public RibbonDecorator(Item item, String description) {
        this.item = item;
        this.description = description;
    }

    @Override
    public double getPrice() {
        return item.getPrice() + 40;
    }
}
