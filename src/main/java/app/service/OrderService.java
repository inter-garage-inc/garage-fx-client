package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Order;
import app.data.order.Item;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class OrderService {

    private ObjectMapper mapper;

    public OrderService() {
        mapper = new ObjectMapper();
    }

    public Boolean ordersSave(Order order) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(order);
            var response = GarageClient.post("/orders", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    public Boolean ordersFindByLicensePlate(Order order) throws ConnectionFailureException {
        try {
            var response = GarageClient.get(order.getId().toString());
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }
}
