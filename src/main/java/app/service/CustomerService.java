package app.service;

import app.client.GarageClient;
import app.data.Customer;
import app.data.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class CustomerService {

    private ObjectMapper mapper;

    public CustomerService() {
        mapper = new ObjectMapper();
    }

    public Boolean register(Customer customer) {
        try {
            var payload = mapper.writeValueAsString(customer);
            var response = GarageClient.post("/customers", payload);
            if (response.statusCode() == 201) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (IOException |  InterruptedException exception) {
            return null;
        }
    }
}