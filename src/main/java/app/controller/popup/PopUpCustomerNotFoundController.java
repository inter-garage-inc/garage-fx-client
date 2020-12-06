package app.controller.popup;

import app.controller.CustomerRegisterController;
import app.router.RouteMapping;
import app.router.Router;

/**
 * @author jlucasrods
 * @version 1.0
 * @since 2020-12-01
 */

@RouteMapping(title = "Cliente Não Encontrado", popup = true)
public class PopUpCustomerNotFoundController {

    /**
     * This method close the pop up using {@link Router}.
     */
    public void handleOnActionClose() {
        Router.closePopUp();
    }

    /**
     * This method call the {@link CustomerRegisterController} and close the pop up.
     */
    public void handleOnActionRegister() {
        Router.closePopUp();
        Router.goTo(CustomerRegisterController.class, true);
    }
}
