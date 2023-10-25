package ua.edu.ucu;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private ObjectMapper objectMapper;

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
    public void testAddFlower() throws Exception {
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
    }
}
