package app.service;

import app.client.ConnectionFailureException;
import app.client.ViaCepClient;
import app.data.Address;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * This class is responsible for loading neighborhood, city, state and country from a postal code in Brazil
 *
 * @author jlucasrods
 * @version 1.0
 * @since 2020-11-20
 */
public class PostalCodesService {

    /**
     * Helper tool to serialize and deserialize POJO and Json to each other
     */
    private final ObjectMapper mapper;

    public PostalCodesService() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * This method is used to get details from the postal code in Brazil
     *
     * @param postalCode the postal code in Brazil
     *                   in the format 00000000
     * @return a Address POJO containing the loaded details or null if nothing is found
     * @throws ConnectionFailureException when request to the server fails
     */
    public Address search(String postalCode) throws ConnectionFailureException {
        try {
            var response = ViaCepClient.get("ws/" + postalCode + "/json/");
            return response.statusCode() == 200 ?
                    mapper.readValue((String) response.body(), new TypeReference<Address>() {}) :
                    null;
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
            throw new ConnectionFailureException();
        }
    }
}
