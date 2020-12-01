package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpServerCloseController;
import app.data.Catalog;
import app.data.Order;
import app.data.order.Item;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogsService;
import app.service.OrdersService;
import app.service.ParkingSpacesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

@RouteMapping(title = "Check in")
public class CheckInController {
    @FXML
    private MainMenuController menuController;

    @FXML
    private Button btnOk;

    @FXML
    private TextField txtLicensePlate;

    @FXML
    private Label lblMessage;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private CheckBox catalogCheckBox;

    private CatalogsService catalogsService;

    private OrdersService ordersService;

    private ParkingSpacesService parkingSpacesService;

    private ArrayList<Catalog> catalogs;

    public CheckInController() {
        catalogsService = new CatalogsService();
        ordersService = new OrdersService();
        parkingSpacesService = new ParkingSpacesService();
        catalogs = new ArrayList<>();
    }

    @FXML
    private void initialize() throws ConnectionFailureException {
        menuController.btnCheckIn.getStyleClass().add("button-menu-selected");

        var layoutY = 10.0;
        for (var catalog : catalogsService.CatalogFindAll()) {
            if (catalog.getStatus() != app.data.catalog.Status.UNAVAILABLE) {
                var response = catalog.getDescription();
                catalogCheckBox = new CheckBox();
                catalogCheckBox.setText(response);
                catalogCheckBox.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
                    if(isSelected) {
                        catalogs.add(catalog);
                    } else {
                        catalogs.remove(catalog);
                    }
                });
                catalogCheckBox.setLayoutY(layoutY);
                catalogCheckBox.setLayoutX(10.0);
                anchorPane.getChildren().addAll(catalogCheckBox);
                layoutY += 20;
            }
        }
    }

    @FXML
    private void handleOnActionButtonOk(ActionEvent actionEvent) throws ConnectionFailureException {
        if(txtLicensePlate.getText().isBlank() || catalogs.isEmpty()) {
            lblMessage.setText("Os campos não podem estar vazios");
            return;
        }

        try {
            if(hasNotOrderOpenForLicensePlate() && hasParkingSpaceVacant()) {
                var order = Order
                        .builder()
                        .items(Item.listOf(catalogs))
                        .licensePlate(txtLicensePlate.getText())
                        .build();
                Router.goTo(CheckInConfirmationController.class, order, true);
            }
        } catch (ConnectionFailureException exception) {
            exception.printStackTrace();
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }

    private Boolean hasNotOrderOpenForLicensePlate() throws ConnectionFailureException {
        var order = ordersService.findByLicensePlate(txtLicensePlate.getText());
        if(order != null) {
            lblMessage.setText("Placa com check in em aberto");
            return false;
        }
        return true;
    }

    private Boolean hasParkingSpaceVacant() throws ConnectionFailureException {
        var parkingSpace = parkingSpacesService.findAllVacant();
        if(parkingSpacesService == null) {
            lblMessage.setText("Não há vagas no momento");
            return false;
        }
        return true;
    }

}
