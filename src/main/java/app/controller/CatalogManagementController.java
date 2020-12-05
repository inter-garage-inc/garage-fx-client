package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpServerCloseController;
import app.data.Catalog;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogsService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * @author FelipePy
 * @version 1.0
 * @since 2020-11-20
 */

@RouteMapping(title = "Gestão de Serviços")
public class CatalogManagementController {

    @FXML
    private TableView<Catalog> tbView;

    @FXML
    private Button btnRegistration;

    @FXML
    private Button btnSelect;

    @FXML
    private Label lblMessage;

    private MainMenuController menuController;

    /**
     * The initialize method receive the data from {@link CatalogsService} that find all {@link Catalog}, And insert the service, price and status in their respective columns.
     */
    public void initialize() {
        menuController.btnServicePlans.getStyleClass().add("button-menu-selected");

        var column1 = new TableColumn<Catalog, String>("Serviço");
        column1.setCellValueFactory(new PropertyValueFactory<Catalog, String>("description"));

        var column2 = new TableColumn<Catalog, String>("Preço");
        column2.setCellValueFactory(new PropertyValueFactory<Catalog, String>("price"));

        var column3 = new TableColumn<Catalog, String>("Status");
        column3.setCellValueFactory(new PropertyValueFactory<Catalog, String>("status"));

        tbView.getColumns().addAll(column1, column2, column3);

        try {
            var service = new CatalogsService();
            var response = service.CatalogFindAll();
            response.forEach(catalog -> {
                tbView.getItems().add(catalog);
            });
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, true);
        }
    }

    /**
     * This method call the class {@link CatalogRegistrationController} using {@link Router}.
     */
    public void handleOnActionButtonBtnRegistration() {
        Router.goTo(CatalogRegistrationController.class, true);
    }

    /**
     * Method that select a {@link Catalog} from table and send to {@link CatalogChangeController} using {@link Router}.
     */
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