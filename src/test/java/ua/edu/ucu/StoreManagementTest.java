package ua.edu.ucu;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import flower.store.FlowerColor;
import flower.store.FlowerType;
import flower.store.items.Flower;
import flower.store.items.FlowerPack;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
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
        this.restTemplate.postForObject("http://localhost:" + port +
                                            "/api/addflower",
                                        request, String.class);

        JsonNode response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 1;
        assert response.get(0).get("sepal_length").asDouble() == 10.2;
        assert response.get(0).get("color").asText().equals("#0000FF");
        assert response.get(0).get("price").asDouble() == 1.1;
        assert response.get(0).get("flower_type").asText().equals("TULIP");

        this.restTemplate.postForObject("http://localhost:" + port +
                                            "/api/removeflower",
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
        this.restTemplate.postForObject("http://localhost:" + port +
                                            "/api/addflowerpack",
                                        request, String.class);

        JsonNode response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 1;
        assert response.get(0).get("flower").get("sepal_length").asDouble() ==
            10.2;
        assert response.get(0).get("flower").get("color").asText().equals(
            "#FF0000");
        assert response.get(0).get("flower").get("price").asDouble() == 1.1;
        assert response.get(0)
            .get("flower")
            .get("flower_type")
            .asText()
            .equals("ROSE");
        assert response.get(0).get("quantity").asDouble() == 10;

        this.restTemplate.postForObject("http://localhost:" + port +
                                            "/api/removeflowerpack",
                                        request, String.class);
        response = this.restTemplate.getForObject(
            "http://localhost:" + port + "/api/getitems", JsonNode.class);
        assert response.isArray();
        assert response.size() == 0;
    }
}
