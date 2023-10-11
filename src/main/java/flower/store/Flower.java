package flower.store;

import flower.store.items.Item;
import lombok.Getter;
import lombok.Setter;

@Setter
public class Flower implements Item {
    @Getter private double sepalLength;
    private FlowerColor color;
    @Getter @Setter private double price;
    @Getter private FlowerType flowerType;

    public Flower() {
        this.sepalLength = 0;
        this.color = FlowerColor.RED;
        this.price = 0;
        this.flowerType = FlowerType.ROSE;
    }

    public Flower(FlowerType flowerType) {
        this.sepalLength = 0;
        this.color = FlowerColor.RED;
        this.price = 0;
        this.flowerType = flowerType;
    }

    public Flower(double sepalLength, FlowerColor color, double price,
                  FlowerType flowerType) {
        this.sepalLength = sepalLength;
        this.color = color;
        this.price = price;
        this.flowerType = flowerType;
    }

    public String getColor() {
        return color.toString();
    }
}
