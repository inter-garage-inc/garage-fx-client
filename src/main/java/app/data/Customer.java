package app.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Customer extends DataBase {
    private String name;

    @JsonProperty("cpf_cnpj")
    private String cpfCnpj;

    private String phone;

    private List<Vehicle> vehicles;

    private List<Order> orders;

    private Address address;
}
