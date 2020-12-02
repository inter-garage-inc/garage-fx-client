package app.controller;

import app.client.ConnectionFailureException;
import app.controller.popup.PopUpRegisterSuccessfulController;
import app.controller.popup.PopUpServerCloseController;
import app.data.Customer;
import app.data.Plan;
import app.data.Vehicle;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CustomersService;
import app.service.PlanService;
import app.service.VehiclesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

@RouteMapping(title = "Gerenciar Veículo do Customer")
public class VehicleRegisterController {
    @FXML
    private TextField fieldLicensePlate;

    @FXML
    private VBox vboxPlans;

    @FXML
    private Label labelMessage;

    private final Customer customer;

    private final CustomersService customersService;

    private final PlanService planService;

    private Plan selectedPlan;

    public VehicleRegisterController() {
        customer = (Customer) Router.getUserData();
        customersService = new CustomersService();
        planService = new PlanService();
    }

    @FXML
    private void initialize() {
        initVboxPlans();
    }

    private void initVboxPlans() {
        try {
            var toggleGroupPlan = new ToggleGroup();
            planService.findAll().forEach(plan -> {
                var button = new RadioButton(plan.getName() + " (" + plan.getType().getValue() + ") " );
                button.getStyleClass().addAll("field", "label");
                button.setToggleGroup(toggleGroupPlan);
                button.setOnAction(event -> selectedPlan = plan);
                vboxPlans.getChildren().add(button);
            });
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }

    @FXML
    private void handleSave(ActionEvent actionEvent) {
        if(fieldLicensePlate.getText().isBlank() || selectedPlan == null) {
            labelMessage.setText("Por favor, preencha todos os campos.");
            return;
        }

        try {
            if(hasNotTheVehicleWithLicensePlate()) {
                var vehicle = Vehicle
                        .builder()
                        .licencePlate(fieldLicensePlate.getText())
                        .plan(selectedPlan)
                        .build();
                var c = Customer
                        .builder()
                        .name(customer.getName())
                        .cpfCnpj(customer.getCpfCnpj())
                        .phone(customer.getPhone())
                        .address(customer.getAddress())
                        .vehicle(vehicle)
                        .build();
                var updated = customersService.update(customer.getId(), c);
                if(updated != null) {
                    Router.showPopUp(PopUpRegisterSuccessfulController.class, 2);
                    Router.goTo(CustomerDetailsController.class, updated);
                } else {
                    labelMessage.setText("Hmmm... estranho! Não foi possível salvar o veículo");
                }
            }
        } catch (ConnectionFailureException exception) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }

    private Boolean hasNotTheVehicleWithLicensePlate() throws ConnectionFailureException {
        if(customersService.findByVehicle(fieldLicensePlate.getText()) != null) {
            labelMessage.setText("O veículo informado já está associado a um cliente");
            return false;
        }
        return true;
    }
}
