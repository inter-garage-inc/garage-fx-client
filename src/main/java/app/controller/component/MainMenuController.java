package app.controller.component;

import app.controller.*;
import app.controller.popup.PlateNotFoundController;
import app.data.user.Role;
import app.router.Router;
import app.service.AuthenticationService;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class MainMenuController {
    public Button btnCheckIn;
    public Button btnCheckout;
    public Button btnMonthly;
    public Button btnOpenAccounts;
    public Button btnVacancyMap;
    public MenuButton btnServicePlans;
    public Button btnUsers;
    public Button btnSelected;
    public MenuItem btnPlansManagement;
    public MenuItem btnCatalogManagement;
    public double y;
    public double x;
    public String text;

    public void setBtnSelected(double x, double y, String text) {
        try {
            btnSelected.setLayoutX(x);
            btnSelected.setLayoutY(y);
            btnSelected.setText(text);
        } catch(Exception e) {
            System.out.println("\nDeu ruim");
        }
    }

    private void processButtonSelected(ActionEvent actionEvent) {
        var nome = (Button) actionEvent.getSource();
        if(btnSelected != null) {
            btnSelected.getStyleClass().removeIf(style -> style.equals("button-selected"));
        }
        btnSelected = nome;
        btnSelected.getStyleClass().add("button-selected");
    }

    public void getSelected(Button button) {
        x = button.getLayoutX();
        y = button.getLayoutY();
        text = button.getText();
    }

    public void initialize() {
        var user = AuthenticationService.claimUser();
        if(user.getRole() == Role.EMPLOYEE) {
            btnServicePlans.setVisible(false); //TODO Test Only
            btnUsers.setVisible(false); //TODO Test Only

        } else if(user.getRole() == Role.MANAGER) {

        } else if(user.getRole() == Role.ADMIN) {

        }
    }

    public void handleOnActionButtonCheckIn(ActionEvent actionEvent) {
        Router.goTo(CheckInController.class, true);
        getSelected(btnCheckIn);
        System.out.printf("%f %f %s", x, y, text);
        setBtnSelected(btnCheckIn.getLayoutX(), btnCheckIn.getLayoutY(), btnCheckIn.getText());
    }

    public void handleOnActionButtonCheckout(ActionEvent actionEvent) {
        Router.goTo(CheckoutController.class, true);
    }

    public void handleOnActionButtonMonthly(ActionEvent actionEvent) {
        Router.goTo(MonthlyCustomerController.class, true);
    }

    public void handleOnActionButtonOrdersOpen(ActionEvent actionEvent) {
        Router.goTo(OrdersOpenController.class, true); // TODO Test Only
    }

    public void handleOnActionButtonVacancyMap(ActionEvent actionEvent) {
        Router.showPopUp(PlateNotFoundController.class); //TODO Test Only
    }

    public void handleOnActionButtonPlansManagement(ActionEvent actionEvent) {

    }

    public void handleOnActionButtonCatalog(ActionEvent actionEvent) {
        Router.goTo(ServiceManagementController.class, true);
    }

    public void handleOnActionButtonUsers(ActionEvent actionEvent) {

    }
}
