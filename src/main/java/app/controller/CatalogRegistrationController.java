package app.controller;

import app.client.ConnectionFailureException;
import app.controller.popup.PopUpRegisterSuccessfulController;
import app.controller.popup.PopUpServerCloseController;
import app.data.Catalog;
import app.data.catalog.CatalogType;
import app.data.catalog.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogsService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.math.BigDecimal;

/**
 * @author FelipePy
 * @version 1.0
 * @since 2020-11-20
 */

@RouteMapping(title = "Cadastro de Serviços")
public class CatalogRegistrationController {

    @FXML
    private TextField fieldService;

    @FXML
    private TextField fieldPrice;

    @FXML
    private ComboBox<Status> cbStatus;

    @FXML
    private Button btnSave;

    public void initialize() {
        cbStatus.getItems().addAll(Status.values());
    }

    /**
     * This method use the service from class {@link CatalogsService} to save a new {@link Catalog}.
     */
    public void handleOnActionButtonBtnSave() {
        var catalog = Catalog.builder()
                .description(fieldService.getText())
                .price(new BigDecimal(fieldPrice.getText()))
                .status(cbStatus.getValue())
                .type(CatalogType.OTHER)
                .build();
        try {
            CatalogsService service = new CatalogsService();
            var response = service.CatalogSave(catalog);
            if(response){
                Router.showPopUp(PopUpRegisterSuccessfulController.class, 1);
            } else {
                System.out.println("Não foi possível realizar o cadastro!!");
            }
            Router.goTo(CatalogManagementController.class);
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }
}