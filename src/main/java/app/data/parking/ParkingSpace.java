package app.data.parking;

import app.data.DataBase;
import app.util.Alphabetic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ParkingSpace extends DataBase {
    private String code;

    private SpaceStatus status;

    private static final Pattern PATTERN = Pattern.compile("^([A-Z]+)([0-9]+)$");

    @JsonIgnore
    @Getter(value=AccessLevel.PRIVATE)
    @Setter(value=AccessLevel.PRIVATE)
    private Matcher matcher;

    public void setCode(String code) {
        this.code = code;
        matcher = PATTERN.matcher(code);
        matcher.find();
    }

    public static class ParkingSpaceBuilder {
        public ParkingSpaceBuilder code(String code) {
            this.code = code;
            matcher = PATTERN.matcher(code);
            matcher.find();
            return this;
        }
    }

    public String getColumnName() {
            return matcher.group(1);
    }

    public String getRowName() {
        return matcher.group(2);
    }

    public Integer getColumnPosition() {
       return Alphabetic.parseString(getColumnName());
    }

    public Integer getRowPosition() {
        return Integer.parseInt(getRowName());
    }
}
