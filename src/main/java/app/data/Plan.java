package app.data;

import app.data.plan.Status;
import app.data.plan.Type;
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

    private Type type;

    private List<Catalog> catalog;

    private BigDecimal price;

    private Status status;
}
