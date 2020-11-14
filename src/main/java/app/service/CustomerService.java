package app.service;

import app.client.GarageClient;
import app.data.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CustomerService {

    private final ObjectMapper mapper;

    public CustomerService() {
        mapper = new ObjectMapper();
    }

    public Boolean save(Customer customer) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(customer);
            var response = GarageClient.post("/customers", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
           throw new ConnectionFailureException();
        }
    }
}