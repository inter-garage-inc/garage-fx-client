package app.data.parking;

import app.data.DataBase;
import app.util.Alphabetic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class ParkingSpace extends DataBase {
    private String code;

    private SpaceStatus status;

    @JsonIgnore
    @Setter(value=AccessLevel.PRIVATE)
    private String columnName;

    @JsonIgnore
    @Setter(value=AccessLevel.PRIVATE)
    private String rowName;

    @JsonIgnore
    @Setter(value=AccessLevel.PRIVATE)
    private Integer columnPosition;

    @JsonIgnore
    @Setter(value=AccessLevel.PRIVATE)
    private Integer rowPosition;

    private static final Pattern PATTERN = Pattern.compile("^([A-Z]+)([0-9]+)$");

    public void setCode(String code) {
        var matcher = PATTERN.matcher(code);
        if(!matcher.find()) {
            throw new IllegalArgumentException("Invalid parking space code");
        }
        this.code = code;
        columnName = matcher.group(1);
        rowName = matcher.group(2);
        columnPosition = Alphabetic.parseString(columnName);
        rowPosition = Integer.parseInt(rowName);
    }

    @Builder
    private static ParkingSpace of(String code, SpaceStatus status) {
        var ps = new ParkingSpace();
        ps.setCode(code);
        ps.setStatus(status);
        return ps;
    }
}
