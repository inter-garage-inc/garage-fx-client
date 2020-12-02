package app.data;

import app.data.parking.ParkingSpace;
import com.fasterxml.jackson.annotation.JsonProperty;
<<<<<<< HEAD
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
=======
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
>>>>>>> main
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Parking extends DataBase {
<<<<<<< HEAD
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("check_in_at")
    private LocalDateTime checkInAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
=======

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonProperty("check_in_at")
    private LocalDateTime checkInAt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
>>>>>>> main
    @JsonProperty("checkout_at")
    private LocalDateTime checkoutAt;

    @JsonProperty("parking_space")
    private ParkingSpace parkingSpace;
}
