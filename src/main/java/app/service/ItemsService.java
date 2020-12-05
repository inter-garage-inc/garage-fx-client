package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.order.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * This class is responsible for the Garage Inc. Items transactions
 * Can get items, save a new one, update it or delete it.
 *
 * @author FelipePy
 * @version 1.0
 * @since 2020-11-17
 */
public class ItemsService {

    /**
     * Helper tool to serialize and deserialize POJO and Json to each other
     */
    private ObjectMapper mapper;

    public ItemsService() {
        mapper = new ObjectMapper();
    }

    /**
     * This method is used to save a new Item
     *
     * @param item POJO
     * @return true if is successfully saved of false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
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
