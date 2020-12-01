package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Catalog;
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
            return mapper.readValue((String) response.body(), new TypeReference<List<Plan>>() {});
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Plan findById(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/plans/"+ id);
            return mapper.readValue((String) response.body(), new TypeReference<Plan>() {});
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Boolean create(Plan plan) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(plan);
            var response = GarageClient.post("/plans", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Boolean update(Plan plan, Long id) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(plan);
            var response = GarageClient.put("/plans/"+id, payload);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Boolean delete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/plans/"+id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}
