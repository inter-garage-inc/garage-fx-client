package app.controller;

import app.router.RouteMapping;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@RouteMapping( title = "Contas abertas Mensalistas")
public class MonthlyOrdersOpenController extends ApplicationController {

    @FXML
    private TableView tbView;

//    public void initialize() {
//        //System.out.println(tbView.getItems().addAll());
//        Order order = new Order();
//        User user = new User();
//        user.setUsername("José");
//        order.setId(1L);
//        order.setCreatedat(new Date());
//        order.setStatus(Status.PAID);
//        order.setTotalamount(new BigDecimal(140.312034123));
//        order.setUser(user);
//        order.setPaymentMethod(PaymentMethod.CARD);
//
//        tbView.getItems().add(order);
//    }
}
