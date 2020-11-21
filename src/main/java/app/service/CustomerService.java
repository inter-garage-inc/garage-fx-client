package app.service;

import app.client.GarageClient;
import app.data.Customer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CustomerService {

    private final ObjectMapper mapper;

    public CustomerService() {
        mapper = new ObjectMapper();
    }

    public Customer save(Customer customer) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(customer);
            var response = GarageClient.post("/customers", payload);
            if(response.statusCode() == 201) {
                return mapper.readValue((String) response.body(), new TypeReference<Customer>(){});
            }
            return null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

}