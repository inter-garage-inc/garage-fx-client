package app.controller.popup;

import app.client.ConnectionFailureException;
import app.controller.CustomerSearchController;
import app.data.Customer;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CustomersService;

/**
 * @author jlucasrods
 * @version 1.0
 * @since 2020-12-01
 */

@RouteMapping(title = "Exclusão de Cliente", popup = true)
public class PopUpCustomerDeleteController {
    private Customer customer;

    private CustomersService customersService;

    /**
     * This constructor method receive the data the {@link Customer} from {@link app.controller.CustomerChangeController} using {@link Router} and instance the {@link CustomersService}.
     */
    public PopUpCustomerDeleteController() {
        customer = (Customer) Router.getUserData();
        customersService = new CustomersService();
    }

    /**
     * This method use the {@link CustomersService} to delete the customer.
     */
    public void handleOnActionDelete() {
        try {
            var response = customersService.delete(customer.getId());
            if(response) {
                Router.showPopUp(PopUpDeleteSuccessController.class, 3);
                Router.goTo(CustomerSearchController.class);
            } else {
                Router.showPopUp(PopUpNotPossibleDeleteController.class, 2);
            }
        } catch (ConnectionFailureException exception) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }
}
