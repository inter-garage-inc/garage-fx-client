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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;

/**
 * @author FelipePy
 * @author jlucasrods
 * @version 1.0
 * @since 2020-12-01
 */

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

    /**
     * The initialize method use the service from class {@link CatalogsService} to find all {@link Catalog} and insert into respective CheckBoxes.
     */
    @FXML
    public void initialize() {
        menuController.btnCheckIn.getStyleClass().add("button-menu-selected");

        var layoutY = 10.0;
        try {
            for (var catalog : catalogsService.CatalogFindAll()) {
                if (catalog.getStatus() != app.data.catalog.Status.UNAVAILABLE) {
                    var response = catalog.getDescription();
                    catalogCheckBox = new CheckBox();
                    catalogCheckBox.setText(response);
                    catalogCheckBox.selectedProperty().addListener((observable, wasSelected, isSelected) -> {
                        if (isSelected) {
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
        } catch (ConnectionFailureException exception) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }

    /**
     * This method check if: text fields is empty and send a {@link Order} to {@link CheckInConfirmationController}
     */
    @FXML
    public void handleOnActionButtonOk() {
        if(txtLicensePlate.getText().isBlank() || catalogs.isEmpty()) {
            lblMessage.setText("Por favor preencha todos os campos.");
            return;
        }

        if(hasNotOrderOpenForLicensePlate() && hasParkingSpaceVacant()) {
            var order = Order
                    .builder()
                    .items(Item.listOf(catalogs))
                    .licensePlate(txtLicensePlate.getText())
                    .build();
            Router.goTo(CheckInConfirmationController.class, order, true);
        }
    }

    /**
     * This method use the service {@link OrdersService} to search a license plate open
     * @return Boolean
     */
    public Boolean hasNotOrderOpenForLicensePlate() {
        try {
            var order = ordersService.findOpenByLicensePlate(txtLicensePlate.getText());
            if (order != null) {
                lblMessage.setText("Placa com check in em aberto");
                return false;
            }
        } catch (ConnectionFailureException exception) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
        return true;
    }

    /**
     * This method use the service {@link ParkingSpacesService} to search for vacant parking space.
     * @return Boolean
     */
    public Boolean hasParkingSpaceVacant()  {
        try {
            var parkingSpace = parkingSpacesService.findAllVacant();
            if (parkingSpacesService == null) {
                lblMessage.setText("Não há vagas no momento");
                return false;
            }
        } catch (ConnectionFailureException exception) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
        return true;
    }

}
