package app.service;

import app.client.GarageClient;
import app.data.Catalog;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class CatalogService {

    private final ObjectMapper mapper;

    public CatalogService() throws ConnectionFailureException {
        mapper = new ObjectMapper();
    }

    public Boolean CatalogSave(Catalog catalog) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(catalog);
            var response = GarageClient.post("/catalogs", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Boolean CatalogUpdate(Catalog catalog) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(catalog);
            var response = GarageClient.put("/catalogs", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Boolean CatalogDelete(Catalog catalog) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(catalog);
            var response = GarageClient.delete(payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}
