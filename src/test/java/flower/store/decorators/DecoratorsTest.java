package flower.store.decorators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flower.store.FlowerColor;
import flower.store.FlowerType;
import flower.store.items.Flower;
import flower.store.items.FlowerBucket;
import flower.store.items.FlowerPack;

public class DecoratorsTest {
    @Test
    public void testBasket() {
        FlowerBucket flowerBucket = new FlowerBucket();
        Flower flower = new Flower(10.0, FlowerColor.RED, 44.5, FlowerType.TULIP, "A nice tulip");
        flowerBucket.add(new FlowerPack(flower, 10));

        BasketDecorator basket = new BasketDecorator(flowerBucket);
        Assertions.assertEquals(449.0, basket.getPrice());
    }

    @Test
    public void testPaper() {
        FlowerBucket flowerBucket = new FlowerBucket();
        Flower flower = new Flower(10.0, FlowerColor.RED, 44.5, FlowerType.TULIP, "A nice tulip");
        flowerBucket.add(new FlowerPack(flower, 10));

        PaperDecorator paper = new PaperDecorator(flowerBucket);
        Assertions.assertEquals(458.0, paper.getPrice());
    }

    @Test
    public void testRibbon() {
        FlowerBucket flowerBucket = new FlowerBucket();
        Flower flower = new Flower(10.0, FlowerColor.RED, 44.5, FlowerType.TULIP, "A nice tulip");
        flowerBucket.add(new FlowerPack(flower, 10));

        RibbonDecorator ribbon = new RibbonDecorator(flowerBucket);
        Assertions.assertEquals(485.0, ribbon.getPrice());
    }
}
