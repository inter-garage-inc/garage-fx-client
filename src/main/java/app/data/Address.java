package app.data;

import app.data.address.Country;
import app.data.address.State;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Address extends DataBase {
    private String street;

    private String number;

    private String complement;

    private String postalCode;

    private String city;

    private State state;

    private Country country;
}
