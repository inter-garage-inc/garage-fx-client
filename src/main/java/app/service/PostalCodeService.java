package app.service;

import app.client.ConnectionFailureException;
import app.client.ViaCepClient;
import app.data.Address;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PostalCodeService {

    private final ObjectMapper mapper;

    public PostalCodeService() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

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
