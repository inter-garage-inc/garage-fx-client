package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Plan;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class PlanService {
    private ObjectMapper mapper;

    public PlanService() {
        mapper = new ObjectMapper();
    }

    public List<Plan> findAll() throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/plans");
            return response.statusCode() == 200 ?
                    mapper.readValue((String) response.body(), new TypeReference<List<Plan>>() {}) :
                    null;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}
