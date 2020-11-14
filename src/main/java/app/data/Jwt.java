package app.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@Data
@NoArgsConstructor
public class Jwt {

    @JsonProperty("token")
    private String token;

    private String getTokenWithoutSignature() {
        return token.substring(0, token.lastIndexOf('.') + 1);
    }

    @SneakyThrows
    public User claimUser() {
        var claims = Jwts.parser().parseClaimsJwt(getTokenWithoutSignature()).getBody();
        var mapper = new ObjectMapper();
        return mapper.readValue(claims.get("user", String.class), User.class);
    }
}
