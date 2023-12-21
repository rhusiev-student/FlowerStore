package flower.store.items;

import flower.store.FlowerColor;
import flower.store.FlowerType;
import jakarta.persistence.Entity;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Entity
public class Flower extends Item {
    @Getter private double sepalLength;
    private FlowerColor color;
    @Getter private double price;
    @Getter private FlowerType flowerType;
    @Getter @Setter private String description;

    public Flower() {
        this.sepalLength = 0;
        this.color = FlowerColor.RED;
        this.price = 0;
        this.flowerType = FlowerType.ROSE;
        this.description = "A Flower";
    }

    public Flower(FlowerType flowerType) {
        this.sepalLength = 0;
        this.color = FlowerColor.RED;
        this.price = 0;
        this.flowerType = flowerType;
        this.description = "A Flower";
    }

    public Flower(double sepalLength, FlowerColor color, double price,
                  FlowerType flowerType) {
        this.sepalLength = sepalLength;
        this.color = color;
        this.price = price;
        this.flowerType = flowerType;
    }

    public Flower(double sepalLength, FlowerColor color, double price,
                  FlowerType flowerType, String description) {
        this.sepalLength = sepalLength;
        this.color = color;
        this.price = price;
        this.flowerType = flowerType;
        this.description = description;
    }

    public String getColor() { return color.toString(); }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Flower)) {
            return false;
        }
        Flower flower = (Flower) obj;
        return flower.sepalLength == sepalLength && flower.color == color
            && flower.price == price && flower.flowerType == flowerType
            && flower.description == description;
    }

    public Map<String, Object> toJson() {
        return Map.of("sepal_length", sepalLength, "color", color.toString(),
                      "price", price, "flower_type", flowerType.toString(),
                      "description", description);
    }

    public static Flower fromJson(Map<String, Object> json) {
        return new Flower((double) json.get("sepal_length"),
                          FlowerColor.fromString((String) json.get("color")),
                          (double) json.get("price"),
                          FlowerType.valueOf((String) json.get("flower_type")),
                          (String) json.get("description"));
    }
}
