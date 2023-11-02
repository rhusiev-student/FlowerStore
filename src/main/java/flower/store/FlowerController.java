package flower.store;

import flower.store.items.Flower;
import flower.store.items.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class FlowerController {
    private final FlowerService flowerService;
    private Map<Integer, ArrayList<Order>> orders =
        new HashMap<Integer, ArrayList<Order>>();

    @Autowired
    public FlowerController(FlowerService flowerService) {
        this.flowerService = flowerService;
    }

    @GetMapping("/flower")
    public List<Map<String, Object>> getItems() {
        List<Map<String, Object>> items = new ArrayList<>();
        for (Item item : flowerService.get()) {
            items.add(item.toJson());
        }
        return items;
    }

    @PostMapping("/flower")
    public void addFlower(@RequestBody Map<String, Object> json) {
        flowerService.add(Flower.fromJson(json));
    }
    
    @PostMapping("/remove")
    public void removeFlower(@RequestBody Map<String, Object> json) {
        flowerService.remove(Flower.fromJson(json));
    }

    @PostMapping("removebyindex")
    public void removeItemByIndex(@RequestBody Map<String, Object> json) {
        flowerService.removeById((int) json.get("index"));
    }

    @GetMapping("/getorders")
    public Map<Integer, ArrayList<Map<String, Object>>> getOrders() {
        Map<Integer, ArrayList<Map<String, Object>>> result =
            new HashMap<Integer, ArrayList<Map<String, Object>>>();
        for (Map.Entry<Integer, ArrayList<Order>> entry : orders.entrySet()) {
            ArrayList<Map<String, Object>> userOrders =
                new ArrayList<Map<String, Object>>();
            for (Order order : entry.getValue()) {
                userOrders.add(order.toJson());
            }
            result.put(entry.getKey(), userOrders);
        }
        return result;
    }

    @PostMapping("/addtempuser")
    public int addTempUser() {
        int userid = -1;
        orders.put(userid, new ArrayList<Order>());
        orders.get(userid).add(new Order());
        orders.get(userid).get(0).addItem(new Flower());
        return userid;
    }

    @PostMapping("/adduser")
    public int addUser(@RequestBody Map<String, Object> json) {
        int userid;
        for (userid = 0; orders.containsKey(userid); ++userid) {
        }
        orders.put(userid, new ArrayList<Order>());
        return userid;
    }

    @PostMapping("/removeuser")
    public void removeUser(@RequestBody Map<String, Object> json) {
        orders.remove((int) json.get("userid"));
    }

    @GetMapping("/getuserorders")
    public ArrayList<Order>
        getUserOrders(@RequestBody Map<String, Object> json) {
        return orders.get((Integer) json.get("userid"));
    }

    @PostMapping("/addorder")
    public void addOrder(@RequestBody Map<String, Object> json) {
        int userid = (int) json.get("userid");
        orders.get(userid).add(new Order());
    }

    @PostMapping("/addorderitem")
    public void addOrderItem(@RequestBody Map<String, Object> json) {
        int userid = (int) json.get("userid");
        Order order = orders.get(userid).get((int) json.get("index"));
        order.addItem(Item.fromJson((Map<String, Object>) json.get("item")));
    }

    @PostMapping("/removeorderbyindex")
    public void removeOrderByIndex(@RequestBody Map<String, Object> json) {
        int userid = (int) json.get("userid");
        orders.get(userid).remove((int) json.get("index"));
    }

    @PostMapping("/setpaymentstrategy")
    public void setPaymentStrategy(@RequestBody Map<String, Object> json) {
        int userid = (int) json.get("userid");
        orders.get(userid).get((int) json.get("index")).setPaymentStrategy(json);
    }

    @PostMapping("/setdeliverystrategy")
    public void setDeliveryStrategy(@RequestBody Map<String, Object> json) {
        int userid = (int) json.get("userid");
        orders.get(userid)
            .get((int) json.get("index"))
            .setDeliveryStrategy(json);
    }

    @PostMapping("/payorder")
    public void payOrder(@RequestBody Map<String, Object> json) {
        int userid = (int) json.get("userid");
        orders.get(userid).get((int) json.get("index")).pay();
    }

    @PostMapping("/deliverorder")
    public void deliverOrder(@RequestBody Map<String, Object> json) {
        int userid = (int) json.get("userid");
        orders.get(userid).get((int) json.get("index")).deliver();
    }

    public static void main(String[] args) {
        SpringApplication.run(FlowerController.class, args);
    }
}
