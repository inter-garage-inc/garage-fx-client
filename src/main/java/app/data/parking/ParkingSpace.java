package app.data.parking;

import app.data.DataBase;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ParkingSpace extends DataBase {
    private String code;

    private SpaceStatus status;
}
