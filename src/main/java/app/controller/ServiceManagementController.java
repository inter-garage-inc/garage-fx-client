
package app.controller;

import app.controller.component.MainMenuController;
import app.data.Catalog;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogService;
import app.client.ConnectionFailureException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

@RouteMapping(title = "Gestão de Serviços")
public class ServiceManagementController {

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

        var service = new CatalogService();
        var response = service.CatalogFindAll();
        response.forEach(catalog -> {
            tbView.getItems().add(catalog);
        });
    }

    public void handleOnActionButtonBtnRegistration() {
        Router.goTo(ServiceRegistrationController.class, true);
    }

    public void handleOnActionButtonBtnSelect() {
        var teste = tbView.getSelectionModel().getSelectedItem();
        Router.goTo(ServiceChangeController.class, teste);
    }
}