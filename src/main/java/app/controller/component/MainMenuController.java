package app.controller.component;

import app.controller.*;
import app.data.user.Role;
import app.router.Router;
import app.service.AuthenticationService;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

/**
 * @author FelipePy
 * @version 1.0
 * @since 2020-12-12
 */

public class MainMenuController {
    public Button btnCheckIn;
    public Button btnCheckout;
    public Button btnMonthly;
    public MenuButton btnOpenAccounts;
    public MenuItem btnCustomerMonthly;
    public MenuItem btnCustomerSingle;
    public Button btnParkingSpacesMap;
    public MenuButton btnServicePlans;
    public Button btnUsers;
    public MenuItem btnPlansManagement;
    public MenuItem btnCatalogManagement;

    /**
     * The initialize method receive a {@link app.data.User} using {@link AuthenticationService} to verify which {@link app.data.User} and block the two last buttons case the {@link app.data.User} be {@link Role#EMPLOYEE}
     */
    public void initialize() {
        var user = AuthenticationService.claimUser();
        if(user.getRole() == Role.EMPLOYEE) {
            btnServicePlans.setVisible(false);
            btnUsers.setVisible(false);

        } else if(user.getRole() == Role.MANAGER) {

        } else if(user.getRole() == Role.ADMIN) {

        }
    }

    /**
     * This method call the {@link CheckInController} using {@link Router}
     */
    public void handleOnActionButtonCheckIn() {
        Router.goTo(CheckInController.class, true);
    }

    /**
     * This method call the {@link CheckoutController} using {@link Router}
     */
    public void handleOnActionButtonCheckout() {
        Router.goTo(CheckoutController.class, true);

    }

    /**
     * This method call the {@link CustomerSearchController} using {@link Router}
     */
    public void handleOnActionButtonMonthly() {
        Router.goTo(CustomerSearchController.class, true);
    }

    /**
     * This method call the {@link CustomerOrdersOpenController} using {@link Router}
     */
    public void handleOnActionButtonBtnCustomerMonthly() {
        Router.goTo(CustomerOrdersOpenController.class, true);
    }

    /**
     * This method call the {@link SingleOrdersOpenController} using {@link Router}
     */
    public void handleOnActionButtonBtnCustomerSingle() {
        Router.goTo(SingleOrdersOpenController.class, true);
    }

    /**
     * This method call the {@link ParkingSpacesMapController} using {@link Router}
     */
    public void handleOnActionButtonParkingSpacesMap() {
        Router.goTo(ParkingSpacesMapController.class, true);
    }

    /**
     * This method call the {@link PlanManagementController} using {@link Router}
     */
    public void handleOnActionButtonPlansManagement() {
        Router.goTo(PlanManagementController.class, true);
    }

    /**
     * This method call the {@link CatalogManagementController} using {@link Router}
     */
    public void handleOnActionButtonCatalog() {
        Router.goTo(CatalogManagementController.class, true);
    }

    /**
     * This method call the {@link PeopleManagementController} using {@link Router}
     */
    public void handleOnActionButtonUsers() {
        Router.goTo(PeopleManagementController.class, true);
    }
}
