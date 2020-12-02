package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.data.Plan;
import app.router.RouteMapping;
import app.router.Router;
import app.service.PlanService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@RouteMapping(title = "Gestão de Planos")
public class PlanManagementController {
    public Label lblMessage;
    @FXML
    private MainMenuController menuController;

    public TableView<Plan> tbView;
    public Button btnRegistration;
    public Button btnSelect;

    public void initialize() throws ConnectionFailureException {
        menuController.btnServicePlans.getStyleClass().add("button-menu-selected");

        var column1 = new TableColumn<Plan, String>("Plano");
        column1.setCellValueFactory(new PropertyValueFactory<Plan, String>("name"));

        var column2 = new TableColumn<Plan, String>("Tipo");
        column2.setCellValueFactory(new PropertyValueFactory<Plan, String>("type"));

        var column3 = new TableColumn<Plan, String>("Preço");
        column3.setCellValueFactory(new PropertyValueFactory<Plan, String>("price"));

        var column4 = new TableColumn<Plan, String>("Status");
        column4.setCellValueFactory(new PropertyValueFactory<Plan, String>("status"));

        tbView.getColumns().addAll(column1, column2, column3, column4);

        var service = new PlanService();
        var plans = service.findAll();
        tbView.getItems().addAll(plans);
    }


    public void handleOnActionButtonBtnRegistration() {
        try {
            Router.goTo(PlanRegistrationController.class, true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void handleOnActionButtonBtnSelect() {
        Boolean response = tbView.getSelectionModel().getSelectedItem() != null;
        if(response) {
            var plan = tbView.getSelectionModel().getSelectedItem();
            Router.goTo(PlanChangeController.class, plan, true);
        } else {
            lblMessage.setText("Por favor, selecione uma opção");
        }
    }
}
