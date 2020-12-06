package app.controller.popup;
import app.client.ConnectionFailureException;
import app.controller.PeopleManagementController;
import app.data.User;
import app.router.RouteMapping;
import app.router.Router;
import app.service.UsersService;
import javafx.scene.control.Button;

/**
 * @author FelipePy
 * @version 1.0
 * @since 2020-11-20
 */

@RouteMapping(title = "Confirmação de exclusão", popup = true)
public class PopUpConfirmDeleteUserController {
    public Button btnDelete;
    public Button btnClose;
    public UsersService service;
    public User user;

    public PopUpConfirmDeleteUserController() {
        service = new UsersService();
    }

    /**
     * This method receive the data the {@link User} from {@link app.controller.UserRegistrationController} using {@link Router} and use the {@link UsersService} to delete the data this user.
     */
    public void handleOnActionButtonBtnDelete() {
        try {
            user = (User) Router.getUserData();

            if (service.userDelete(user.getId())) {
                Router.showPopUp(PopUpDeleteSuccessController.class, 2);
                Router.goTo(PeopleManagementController.class);
            } else {
                Router.showPopUp(PopUpNotPossibleDeleteController.class, 2);
                Router.goTo(PeopleManagementController.class);
            }
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }

    /**
     * This method close the pop up using {@link Router}.
     */
    public void handleOnActionButtonBtnClose() {
        Router.closePopUp();
    }
}
