package ua.edu.ucu;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import flower.store.items.FlowerBucket;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class StoreManagementTest {
    @Autowired private TestRestTemplate restTemplate;

    @LocalServerPort private int port;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void apiExists() throws Exception {
        assert this.restTemplate.getForObject("http://localhost:" + port + "/",
                                              String.class) != null;
    }

    @Test
    public void testGetItems() throws Exception {
        String response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", String.class);
        assert response.equals("[]");
    }

    @Test
    public void testAddRemoveFlower() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(
            "{\"sepal_length\": 10.2, \"color\": \"BLUE\", \"price\": 1.1, \"flower_type\": \"TULIP\"}",
            headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/addflower",
                                        request, String.class);

        JsonNode response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 1;
        assert response.get(0).get("sepal_length").asDouble() == 10.2;
        assert response.get(0).get("color").asText().equals("#0000FF");
        assert response.get(0).get("price").asDouble() == 1.1;
        assert response.get(0).get("flower_type").asText().equals("TULIP");

        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeflower",
                                        request, String.class);
        response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 0;
    }

    @Test
    public void testAddRemoveFlowerPack() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestString =
            "{\"flower\": {\"sepal_length\": 10.2, \"color\": \"RED\", \"price\": 1.1, \"flower_type\": \"ROSE\"}, \"quantity\": 10}";
        HttpEntity<String> request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/addflowerpack",
                                        request, String.class);

        JsonNode response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 1;
        assert response.get(0).get("flower").get("sepal_length").asDouble()
            == 10.2;
        assert response.get(0).get("flower").get("color").asText().equals(
            "#FF0000");
        assert response.get(0).get("flower").get("price").asDouble() == 1.1;
        assert response.get(0)
            .get("flower")
            .get("flower_type")
            .asText()
            .equals("ROSE");
        assert response.get(0).get("quantity").asDouble() == 10;

        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeflowerpack",
                                        request, String.class);
        response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 0;
    }

    @Test
    public void testAddRemoveFlowerBucket() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestString =
            "{\"items\": [{\"sepal_length\": 10.2, \"color\": \"RED\", \"price\": 1.1, \"flower_type\": \"ROSE\"}, {\"flower\": {\"sepal_length\": 10.2, \"color\": \"RED\", \"price\": 1.1, \"flower_type\": \"ROSE\"}, \"quantity\": 10}]}";
        Map<String, Object> requestMap =
            objectMapper.readValue(requestString, Map.class);
        FlowerBucket bucket = FlowerBucket.fromJson(requestMap);
        HttpEntity<String> request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/addflowerbucket",
                                        request, String.class);

        JsonNode response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 1;
        JsonNode flowerBucket = response.get(0);
        assert flowerBucket.get("items").isArray();
        assert flowerBucket.get("items").size() == 2;
        JsonNode flower = flowerBucket.get("items").get(0);
        assert flower.get("sepal_length").asDouble() == 10.2;
        assert flower.get("color").asText().equals("#FF0000");
        assert flower.get("price").asDouble() == 1.1;
        assert flower.get("flower_type").asText().equals("ROSE");
        JsonNode flowerPack = flowerBucket.get("items").get(1);
        assert flowerPack.get("flower").get("sepal_length").asDouble() == 10.2;
        assert flowerPack.get("flower").get("color").asText().equals("#FF0000");
        assert flowerPack.get("flower").get("price").asDouble() == 1.1;
        assert flowerPack.get("flower")
            .get("flower_type")
            .asText()
            .equals("ROSE");
        assert flowerPack.get("quantity").asDouble() == 10;

        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeflowerbucket",
                                        request, String.class);
        response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 0;
    }

    @Test
    public void testRemoveItemByIndex() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestString =
            "{\"items\": [{\"sepal_length\": 10.2, \"color\": \"RED\", \"price\": 1.1, \"flower_type\": \"ROSE\"}, {\"flower\": {\"sepal_length\": 10.2, \"color\": \"RED\", \"price\": 1.1, \"flower_type\": \"ROSE\"}, \"quantity\": 10}]}";
        HttpEntity<String> request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/addflowerbucket",
                                        request, String.class);

        JsonNode response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 1;

        requestString =
            "{\"sepal_length\": 10.2, \"color\": \"RED\", \"price\": 1.1, \"flower_type\": \"ROSE\"}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/addflower",
                                        request, String.class);

        response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 2;

        requestString = "{\"index\": 1}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeitembyindex",
                                        request, String.class);

        response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 1;
        JsonNode flowerBucket = response.get(0);
        assert flowerBucket.get("items").isArray();

        requestString = "{\"index\": 0}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeitembyindex",
                                        request, String.class);

        response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 0;
    }

    @Test
    public void testGetOrders() throws Exception {
        JsonNode response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getorders", JsonNode.class);
        assert response.isObject();
        assert response.size() == 0;
    }

    @Test
    public void testAddRemoveUser() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestString =
            "{\"sepal_length\": 10.2, \"color\": \"RED\", \"price\": 1.1, \"flower_type\": \"ROSE\"}";
        HttpEntity<String> request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/addflower",
                                        request, String.class);

        requestString = "{}";
        request = new HttpEntity<>(requestString, headers);
        int userid = this.restTemplate.postForObject(
            "http://localhost:" + port + "/api/adduser", request,
            Integer.class);
        assert userid == 0;

        requestString = "{\"userid\": " + userid + "}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeuser",
                                        request, String.class);
        JsonNode response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getorders", JsonNode.class);
        assert response.isObject();
        assert response.size() == 0;

        requestString = "{\"index\": 0}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeitembyindex",
                                        request, String.class);
    }

    @Test
    public void testAddRemoveOrder() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestString =
            "{\"sepal_length\": 10.2, \"color\": \"RED\", \"price\": 1.1, \"flower_type\": \"ROSE\"}";
        HttpEntity<String> request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/addflower",
                                        request, String.class);

        requestString = "{}";
        request = new HttpEntity<>(requestString, headers);
        int userid = this.restTemplate.postForObject(
            "http://localhost:" + port + "/api/adduser", request,
            Integer.class);

        requestString = "{\"userid\": " + userid + "}";
        request = new HttpEntity<>(requestString, headers);
        Map<String, Object> result = this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/addorder",
                                        request, Map.class);
        System.out.println(result);

        JsonNode response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getorders", JsonNode.class);
        assert response.isObject();
        assert response.get("0").isArray();
        assert response.get("0").size() == 1;
        assert response.get("0").get(0).get("items").isArray();

        requestString = "{\"userid\": " + userid + ", \"index\": 0}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeorderbyindex",
                                        request, String.class);
        response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getorders", JsonNode.class);
        assert response.isObject();
        assert response.get("0").isArray();
        assert response.get("0").size() == 0;

        requestString = "{\"userid\": " + userid + "}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeuser",
                                        request, String.class);

        requestString = "{\"index\": 0}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeitembyindex",
                                        request, String.class);
    }

    @Test
    public void testPaymentStrategy() throws Exception {
        // Create user, an empty order for them, set payment strategy
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestString = "{}";
        HttpEntity<String> request = new HttpEntity<>(requestString, headers);
        int userid = this.restTemplate.postForObject(
            "http://localhost:" + port + "/api/adduser", request,
            Integer.class);
        requestString = "{\"userid\": " + userid + "}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/addorder",
                                        request, String.class);
        requestString =
            "{\"userid\": " + userid
            + ", \"index\": 0, \"type\": \"credit_card\", \"amount\": 100}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/setpaymentstrategy",
                                        request, String.class);

        // Check whether payment strategy is set
        JsonNode response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getorders", JsonNode.class);
        assert response.isObject();
        assert response.get("0").isArray();
        assert response.get("0").size() == 1;
        assert response.get("0").get(0).get("payment_strategy").isObject();
        assert response.get("0")
            .get(0)
            .get("payment_strategy")
            .get("type")
            .asText()
            .equals("credit_card");
        assert response.get("0")
                .get(0)
                .get("payment_strategy")
                .get("amount")
                .asInt() == 100;

        // Delete user
        requestString = "{\"userid\": " + userid + "}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeuser",
                                        request, String.class);
    }

    @Test
    public void testDeliveryStrategy() throws Exception {
        // Create user, an empty order for them, set delivery strategy
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestString = "{}";
        HttpEntity<String> request = new HttpEntity<>(requestString, headers);
        int userid = this.restTemplate.postForObject(
            "http://localhost:" + port + "/api/adduser", request,
            Integer.class);
        requestString = "{\"userid\": " + userid + "}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/addorder",
                                        request, String.class);
        requestString =
            "{\"userid\": " + userid
            + ", \"index\": 0, \"type\": \"dhl\", \"address\": \"address\"}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/setdeliverystrategy",
                                        request, String.class);

        // Check whether delivery strategy is set
        JsonNode response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getorders", JsonNode.class);
        assert response.isObject();
        assert response.get("0").isArray();
        assert response.get("0").size() == 1;
        assert response.get("0").get(0).get("delivery_strategy").isObject();
        assert response.get("0")
            .get(0)
            .get("delivery_strategy")
            .get("type")
            .asText()
            .equals("dhl");
        assert response.get("0")
            .get(0)
            .get("delivery_strategy")
            .get("address")
            .asText()
            .equals("address");

        // Delete user
        requestString = "{\"userid\": " + userid + "}";
        request = new HttpEntity<>(requestString, headers);
        this.restTemplate.postForObject("http://localhost:" + port
                                            + "/api/removeuser",
                                        request, String.class);
    }
}
