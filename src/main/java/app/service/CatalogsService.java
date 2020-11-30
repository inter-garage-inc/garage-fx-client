package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Catalog;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class CatalogsService {

    private final ObjectMapper mapper;

    public CatalogsService() {
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

    public Boolean CatalogUpdate(Catalog catalog, Long id) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(catalog);
            var response = GarageClient.put("/catalogs/"+id, payload);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public Boolean CatalogDelete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/catalogs/"+id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    public List<Catalog> CatalogFindAll() throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/catalogs");
            return mapper.readValue( (String) response.body(), new TypeReference<List<Catalog>> () {});
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            throw new ConnectionFailureException();
        }
    }

    public Catalog findBy(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/catalogs/" + id);
            return mapper.readValue((String) response.body(), new TypeReference<Catalog>() {});
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}
