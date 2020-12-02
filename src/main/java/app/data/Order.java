package app.data;

import app.data.order.Item;
import app.data.order.PaymentMethod;
import app.data.order.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Order extends DataBase {
    private List<Item> items;

    private Customer customer;

    private User user;

    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @JsonProperty("payment_method")
    private PaymentMethod paymentMethod;

    @JsonProperty("license_plate")
    private String licensePlate;

    private Status status;

}
