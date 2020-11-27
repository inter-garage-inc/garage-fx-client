package app.controller;

import app.client.ConnectionFailureException;
import app.controller.popup.PopUpServerCloseController;
import app.data.Customer;
import app.router.RouteMapping;
import app.router.Router;
import app.service.PlanService;
import app.service.VehicleService;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

@RouteMapping(title = "Editar veículo")
public class VehicleRegisterController {
    @FXML
    private VBox vboxPlans;

    @FXML
    private Text comboBoxCurrentOrderStatus;

    private final Customer customer;

    private final PlanService planService;

    private final VehicleService vehicleService;

    public VehicleRegisterController() {
        customer = (Customer) Router.getUserData();
        planService = new PlanService();
        vehicleService = new VehicleService();
    }

    public void initialize() {
        initVboxPlans();
    }

    private void initVboxPlans() {
        try {
            var availablePlans = planService.findAll();
            availablePlans.forEach(plan -> {
                var button = new RadioButton(plan.getName());
                button.getStyleClass().addAll("field", "label");
                vboxPlans.getChildren().add(button);
            });
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }
}
