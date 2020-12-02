package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpServerCloseController;
import app.data.Catalog;
import app.data.Order;
import app.data.OrderItem;
import app.data.Parking;
import app.data.order.Item;
import app.data.order.Status;
import app.data.parking.ParkingSpace;
import app.router.RouteMapping;
import app.router.Router;
import app.service.OrdersService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

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

    public void handleOnActionButtonBtnSelect() {
        Boolean response = tbView.getSelectionModel().getSelectedItem() != null;
        if(response) {
            var catalog = tbView.getSelectionModel().getSelectedItem();
            Router.goTo(CatalogChangeController.class, catalog, true);
        } else {
            lblMessage.setText("Por favor, selecione uma opção");
        }
    }
}