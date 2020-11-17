package app.data;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Plan extends DataBase {
    private String name;

    private String description;

    private List<Catalog> catalog;

    private BigDecimal price;
}
