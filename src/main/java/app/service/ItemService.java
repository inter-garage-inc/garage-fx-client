package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.order.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ItemService {

<<<<<<< HEAD
<<<<<<< HEAD

=======
    private ObjectMapper mapper;

    public ItemService() {
        mapper = new ObjectMapper();
    }

    public Boolean itemsSave(Item item) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(item);
            var response = GarageClient.post("/items", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    public Boolean itemFindByLicensePlate(Item item) throws ConnectionFailureException {
        try {
            var response = GarageClient.get(item.getParking().getLicensePlate());
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }
>>>>>>> d3e240c... pin
=======

>>>>>>> 9058e58... pin

}
