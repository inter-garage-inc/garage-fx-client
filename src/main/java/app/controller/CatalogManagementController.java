
package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.data.Catalog;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogsService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@RouteMapping(title = "Gestão de Serviços")
public class CatalogManagementController {

    public TableView<Catalog> tbView;
    public Button btnRegistration;
    public Button btnSelect;

    @FXML
    private MainMenuController menuController;

    public void initialize() throws ConnectionFailureException {
        menuController.btnServicePlans.getStyleClass().add("button-menu-selected");

        var column1 = new TableColumn<Catalog, String>("Serviço");
        column1.setCellValueFactory(new PropertyValueFactory<Catalog, String>("description"));

        var column2 = new TableColumn<Catalog, String>("Preço");
        column2.setCellValueFactory(new PropertyValueFactory<Catalog, String>("price"));

        var column3 = new TableColumn<Catalog, String>("Status");
        column3.setCellValueFactory(new PropertyValueFactory<Catalog, String>("status"));

        tbView.getColumns().addAll(column1, column2, column3);

        var service = new CatalogsService();
        var response = service.CatalogFindAll();
        response.forEach(catalog -> {
            tbView.getItems().add(catalog);
        });
    }

    public void handleOnActionButtonBtnRegistration() {
        Router.goTo(CatalogRegistrationController.class, true);
    }

    public void handleOnActionButtonBtnSelect() {
        var catalog = tbView.getSelectionModel().getSelectedItem();
        Router.goTo(CatalogChangeController.class, catalog, true);
    }
}