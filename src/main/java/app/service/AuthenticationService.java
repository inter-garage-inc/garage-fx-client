package app.service;

import app.client.GarageClient;
import app.data.Credentials;
import app.data.Jwt;
import app.data.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;

import java.io.IOException;

public class AuthenticationService {

    private ObjectMapper mapper;

    private static Jwt jwt;

    public AuthenticationService() {
        mapper = new ObjectMapper();
    }

    public Boolean login(Credentials authRequest) {
        try {
            var payload = mapper.writeValueAsString(authRequest);
            var response = GarageClient.post("/authentication", payload);
            if(response.statusCode() == 200) {
                jwt = mapper.readValue((String) response.body(), new TypeReference<Jwt>(){});
                return Boolean.TRUE;
            } else {
        return Boolean.FALSE;
            }
        } catch (IOException | InterruptedException exception) {
            return null;
        }
    }

    @SneakyThrows
    public User claimUser() {
        var claims = getJwtClaims();
        var userJson = claims.get("user", String.class);
        return mapper.readValue(userJson, User.class);
    }

    private String getJwtWithoutSignature() {
        var i = jwt.getToken().lastIndexOf('.');
        return jwt.getToken().substring(0, i + 1);
    }

    private Claims getJwtClaims() {
        return Jwts.parser()
                .parseClaimsJwt(getJwtWithoutSignature())
                .getBody();
    }

    public static String getToken() {
        return (jwt != null) ? jwt.getToken() : "";
    }
}
