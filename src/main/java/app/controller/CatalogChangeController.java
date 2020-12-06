package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpDeleteSuccessController;
import app.controller.popup.PopUpServerCloseController;
import app.data.Catalog;
import app.data.catalog.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogsService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.math.BigDecimal;

/**
 * @author FelipePy
 * @version 1.0
 * @since 2020-11-20
 */

@RouteMapping(title = "Alteração de Serviços")
public class CatalogChangeController {

    @FXML
    private Button btnAlter;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField fieldService;

    @FXML
    private TextField fieldPrice;

    @FXML
    private ComboBox<Status> cbStatus;

    @FXML
    private Label lblMessage;

    private Catalog catalog;

    private MainMenuController menuController;

    /**
     * The initialize method receive the data the {@link Catalog} from {@link CatalogManagementController} and insert in their respective fields.
     */
    public void initialize() {
        menuController.btnCatalogManagement.getStyleClass().add("button-menu-selected");
        catalog = (Catalog) Router.getUserData();
        fieldPrice.setText(catalog.getPrice().toString());
        fieldService.setText(catalog.getDescription());

        cbStatus.getItems().addAll(Status.values());
        cbStatus.setValue(catalog.getStatus());
    }

    /**
     * This Method check if: The fields is empty and save the changes of {@link Catalog} using the class {@link CatalogsService}.
     */
     public void handleOnActionButtonBtnAlter() {
        try {
            Boolean priceNull = fieldPrice.getText().isBlank();
            Boolean serviceNull = fieldService.getText().isBlank();
            Boolean cbStatusNull = cbStatus.getValue() == null;

            if(cbStatusNull || priceNull || serviceNull) {
                lblMessage.setText("Todos os campos precisam ser preenchidos");
                return;
            }

            var catalog2 = Catalog.builder()
                    .description(fieldService.getText())
                    .price(new BigDecimal(fieldPrice.getText()))
                    .status(cbStatus.getValue())
                    .build();

            CatalogsService service = new CatalogsService();
            service.CatalogUpdate(catalog2, catalog.getId());

            Router.goTo(CatalogManagementController.class);
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, true);
        }
    }

    /**
     * This method use the service from class {@link CatalogsService} to delete a {@link app.data.User}.
     */
    public void handleOnActionButtonBtnDelete() { // TODO Verificar se o serviço esta cadastrado a um plano
        try {
            CatalogsService service = new CatalogsService();
            var response = service.CatalogDelete(catalog.getId());
            if (response) {
                Router.showPopUp(PopUpDeleteSuccessController.class);
                Router.goTo(CatalogManagementController.class);
            } else {
                lblMessage.setText("Não foi possível deletar o serviço.\nTente novamente mais tarde.");
            }
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }

    }


}
