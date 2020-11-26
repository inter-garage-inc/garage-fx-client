package app.controller.popup;
import app.client.ConnectionFailureException;
import app.controller.PeopleManagementController;
import app.data.User;
import app.router.RouteMapping;
import app.router.Router;
import app.service.UserService;
import javafx.scene.control.Button;


@RouteMapping(title = "Confirmação de exclusão", popup = true)
public class PopUpConfirmDeleteUserController {
    public Button btnDelete;
    public Button btnClose;
    public UserService service;
    public User user;

    public PopUpConfirmDeleteUserController() {
        service = new UserService();
    }

    public void handleOnActionButtonBtnDelete() {
        try {
            user = (User) Router.getUserData();

            if(service.userDelete(user.getId())) {
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


    public void handleOnActionButtonBtnClose() {
        Router.closePopUp();
    }
}
