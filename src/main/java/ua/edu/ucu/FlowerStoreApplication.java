package ua.edu.ucu;

import flower.store.Flower;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class FlowerStoreApplication {
    private ArrayList<Flower> flowers = new ArrayList<Flower>();

    @GetMapping("/getFlowers")
    public List<Flower> getFlowers() {
        return flowers;
    }

    @GetMapping("/addFlower")
    public void addFlower(Flower flower) {
        flowers.add(flower);
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowerStoreApplication.class, args);
    }
}
