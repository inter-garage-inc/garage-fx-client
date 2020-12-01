package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.order.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ItemsService {
    private ObjectMapper mapper;

    public ItemsService() {
        mapper = new ObjectMapper();
    }

    public Boolean save(Item item) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(item);
            var response = GarageClient.post("/items", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }
}
