package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class OrderService {

    private ObjectMapper mapper;

    public OrderService() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public Boolean ordersSave(Order order) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(order);
            var response = GarageClient.post("/orders", payload);
            System.out.println(response.body());
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            throw new ConnectionFailureException(exception);
        }
    }

    public Boolean ordersFindByLicensePlate(String licensePlate) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/orders/license-plate/" + licensePlate);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }
}
