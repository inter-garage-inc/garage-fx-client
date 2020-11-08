package app.service;

import app.client.GarageClient;
import app.data.AuthRequest;
import app.data.AuthResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@Service
public class AuthenticationService {

    private ObjectMapper mapper;

    public AuthResponse login(AuthRequest authRequest) throws IOException, InterruptedException {
        mapper = new ObjectMapper();
        var payload = mapper.writeValueAsString(authRequest);
        var response= GarageClient.post("/authentication",  payload);
        if(response.statusCode() == 200)
            return mapper.readValue((String) response.body(), new TypeReference<AuthResponse>(){});
        else
            return null;
    }
}
