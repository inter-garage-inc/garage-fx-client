package app.service;

import app.client.GarageClient;
import app.data.AuthRequest;
import app.data.AuthResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AuthenticationService {

    private ObjectMapper mapper;

    private AuthResponse authResponse;

    public AuthenticationService() {
        mapper = new ObjectMapper();
    }

    public Boolean login(AuthRequest authRequest) {
        try {
            var payload = mapper.writeValueAsString(authRequest);
            var response = GarageClient.post("/authentication", payload);
            if(response.statusCode() == 200) {
                authResponse = mapper.readValue((String) response.body(), new TypeReference<AuthResponse>(){});
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } catch (IOException | InterruptedException exception) {
            return null;
        }
    }

    public String recoverToken() {
        return authResponse.getToken();
    }
}
