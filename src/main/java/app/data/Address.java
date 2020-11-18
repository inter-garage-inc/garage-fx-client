package app.data;

import app.data.address.Country;
import app.data.address.State;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Address extends DataBase {
    @JsonAlias("logradouro")
    private String street;

    private String number;

    @JsonAlias("complemento")
    private String complement;

    @JsonAlias("cep")
    @JsonProperty("postal_code")
    private String postalCode;

    @JsonAlias("bairro")
    private String neighborhood;

    @JsonAlias("localidade")
    private String city;

    @JsonAlias("uf")
    private State state;

    private Country country;
}
