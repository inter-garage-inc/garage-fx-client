package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpServerCloseController;
import app.data.Order;
import app.data.OrderItem;
import app.data.order.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.OrdersService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FelipePy
 * @version 1.0
 * @since 2020-11-30
 */

@RouteMapping(title = "Ordens de Serviço Abertas")
public class SingleOrdersOpenController {

    public Button btnSrc;
    public TableView tbView;
    public Label lblMessage;
    private List<Order> order;
    private OrdersService ordersService;

    @FXML
    private MainMenuController menuController;

    public SingleOrdersOpenController() {
        ordersService = new OrdersService();
        tbView = new TableView();
    }

    /**
     * The initialize method receive the data from {@link OrdersService} that find all {@link app.data.order.Item}, And insert the services and license plate in their respective columns.
     */
    public void initialize() {
        menuController.btnOpenAccounts.getStyleClass().add("button-menu-selected");

        var column1 = new TableColumn<OrderItem, String>("Serviços");
        column1.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("description"));

        var column2 = new TableColumn<OrderItem, String>("Placa");
        column2.setCellValueFactory(new PropertyValueFactory<OrderItem, String>("licensePlate"));

        tbView.getColumns().addAll(column1, column2);
        try {
            order = ordersService.findAll().stream().filter(order ->
                    !order.getStatus().equals(Status.PAID)).collect(Collectors.toList());
            order.forEach(order1 -> {
                order1.getItems().forEach(item -> {
                    var orderItem = OrderItem.builder()
                            .description(item.getDescription())
                            .licensePlate(order1.getLicensePlate())
                            .build();
                    tbView.getItems().add(orderItem);
                });
            });

        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class);
        }
    }

    /**
     * This method select a {@link Order} and send to {@link CatalogChangeController} using {@link Router}
     */
    public void handleOnActionButtonBtnSelect() {
        Boolean response = tbView.getSelectionModel().getSelectedItem() != null;
        if(response) {
            var order = tbView.getSelectionModel().getSelectedItem();
            Router.goTo(CatalogChangeController.class, order, true);
        } else {
            lblMessage.setText("Por favor, selecione uma opção");
        }
    }
}