package app.service;

import app.client.ConnectionFailureException;
import app.client.GarageClient;
import app.data.Catalog;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for Garage Inc. catalog transactions
 * Can get the catalogs, save a new one, update it or delete it.
 *
 * @author FelipePy
 * @version 1.0
 * @since 2020-11-20
 */
public class CatalogsService {

    /**
     * Helper tool to serialize and deserialize POJO and Json to each other
     */
    private final ObjectMapper mapper;

    public CatalogsService() {
        mapper = new ObjectMapper();
    }

    /**
     * This method is used to save a new Catalog
     *
     * @param catalog a POJO
     * @return true if successfully saved or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean CatalogSave(Catalog catalog) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(catalog);
            var response = GarageClient.post("/catalogs", payload);
            return response.statusCode() == 201;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to update a Catalog
     *
     * @param catalog a POJO
     * @param id the Catalog id
     * @return true if successfully updated or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean CatalogUpdate(Catalog catalog, Long id) throws ConnectionFailureException {
        try {
            var payload = mapper.writeValueAsString(catalog);
            var response = GarageClient.put("/catalogs/"+id, payload);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to delete a Catalog
     *
     * @param id the Catalog id
     * @return true if successfully deleted or false otherwise
     * @throws ConnectionFailureException when request to the server fails
     */
    public Boolean CatalogDelete(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.delete("/catalogs/"+id);
            return response.statusCode() == 200;
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to get all the Catalogs
     *
     * @return a List of Catalog POJOs
     * @throws ConnectionFailureException when request to the server fails
     */
    public List<Catalog> CatalogFindAll() throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/catalogs");
            return mapper.readValue( (String) response.body(), new TypeReference<List<Catalog>> () {});
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            throw new ConnectionFailureException();
        }
    }

    /**
     * This method is used to get one Catalog
     *
     * @param id the Catalog id
     * @return Catalog POJO
     * @throws ConnectionFailureException when request to the server fails
     */
    public Catalog findBy(Long id) throws ConnectionFailureException {
        try {
            var response = GarageClient.get("/catalogs/" + id);
            return mapper.readValue((String) response.body(), new TypeReference<Catalog>() {});
        } catch (IOException | InterruptedException exception) {
            throw new ConnectionFailureException();
        }
    }
}
