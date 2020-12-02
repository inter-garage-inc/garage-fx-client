package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Order;
import app.data.Plan;
import app.data.order.PaymentMethod;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OrdersService {

    private ObjectMapper mapper;

    public OrdersService() {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public Order create(Order order) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(order);
            var response = GarageClient.post("/orders", payload);
            System.out.println(response.body());
            return response.statusCode() == 201
                    ? mapper.readValue((String) response.body(), new TypeReference<Order>() {})
                    : null;
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            throw new ConnectionFailureException(exception);
        }
    }

    public Order findOpenByLicensePlate(String licensePlate) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/orders/open/license-plate/" + URLEncoder.encode(licensePlate, StandardCharsets.UTF_8));
            return response.statusCode() == 200
                    ? mapper.readValue((String) response.body(), new TypeReference<Order>() {})
                    : null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    public Boolean close(Order order) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(order);
            var response = GarageClient.post("/orders/close", payload);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    public Boolean update(Long id, Order order) throws  ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(order);
            var response = GarageClient.put("/orders"+id, payload);
            System.out.println(response.body());
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    public List<Order> findAll() throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/orders");
            return mapper.readValue((String) response.body(), new TypeReference<List<Order>>() {});
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}
