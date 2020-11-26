package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
<<<<<<< HEAD
import app.data.Parking;
=======
>>>>>>> 90830fd... pin
import app.data.parking.ParkingSpace;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ParkingSpacesService {
    private ObjectMapper mapper;

    public ParkingSpacesService() {
        mapper = new ObjectMapper();
    }

    public List<ParkingSpace> index() throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/parking-spaces");
            return response.statusCode() == 200 ?
                    mapper.readValue((String) response.body(), new TypeReference<List<ParkingSpace>>() {}) :
                    null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    public Boolean save(ParkingSpace parkingSpace) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(parkingSpace);
            var response = GarageClient.post("/parking-spaces", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    public Boolean update(Long id, ParkingSpace parkingSpace) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(parkingSpace);
            var response = GarageClient.put("/parking-spaces/" + id, payload);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    public Boolean delete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/parking-spaces/" + id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }

    public ParkingSpace findAvailable() throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/parking-spaces/available");
            return response.statusCode() == 200 ? mapper.readValue((String) response.body(), new TypeReference<ParkingSpace>() {}) : null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException(exception);
        }
    }
}
