package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpServerCloseController;
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

import java.util.List;

/**
 * @author Ttarora
 * @version 1.0
 * @since 2020-12-01
 */

@RouteMapping(title = "Gestão de Planos")
public class PlanManagementController {
    @FXML
    private Label lblMessage;

    @FXML
    private TableView<Plan> tbView;

    @FXML
    private Button btnRegistration;

    @FXML
    private Button btnSelect;

    private MainMenuController menuController;
    /**
     * The initialize method receive the data from {@link PlanService} that find all {@link Plan}, And insert the plan, type, price and status in their respective columns.
     */
    public void initialize() {
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
        List<Plan> plans = null;
        try {
            plans = service.findAll();
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
        tbView.getItems().addAll(plans);
    }

    /**
     * This method call {@link PlanRegistrationController} using {@link Router}
     */
    public void handleOnActionButtonBtnRegistration() {
        try {
            Router.goTo(PlanRegistrationController.class, true);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method select a {@link Plan} and send to {@link PlanChangeController} using {@link Router}
     */
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
