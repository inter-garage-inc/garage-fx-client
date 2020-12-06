package app.controller.component;

import app.controller.LoginController;
import app.router.Router;
import app.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author jlucasrods
 * @version 1.0
 * @since 2020-11-18
 */

public class UserDetailsController {
    @FXML
    private Label labelName;

    /**
     * The initialize method receive the data the {@link app.data.User} using {@link Router} and insert into respective fields
     */
    public void initialize() {
        var user = AuthenticationService.claimUser();
        labelName.setText(user.getName());
    }

    /**
     * This method performs the logout from application
     */
    public void handleOnActionButtonLogout() {
        AuthenticationService.logout();
        Router.goTo(LoginController.class);
        Router.reOpenEffect();
    }
}
