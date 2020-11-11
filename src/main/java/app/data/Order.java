package app.data;

import app.data.order.Status;
import app.data.order.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class Order {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("createdat")
    private Date createdat;

    @JsonProperty("status")
    private Status status;
    
    @JsonProperty("totalamount")
    private BigDecimal totalamount;

    @JsonProperty("user")
    private User user;

    @JsonProperty("paymentmethod")
    private PaymentMethod paymentMethod;

    // TODO Terminar classe de items
//    @JsonProperty("items")
//    private List<item>;

}
