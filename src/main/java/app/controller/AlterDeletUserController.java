package app.controller;

import app.controller.popup.PopUpAlterSuccessfulController;
import app.data.User;
import app.data.user.Role;
import app.data.user.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.ConnectionFailureException;
import app.service.UserService;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@RouteMapping
public class AlterDeletUserController {

    public TextField fieldName;
    public TextField fieldUsername;
    public PasswordField fieldPassword;
    public PasswordField fieldConfPassword;
    public Button btnAlter;
    public Label lblMessage;
    public Button btnDelete;
    User user;
    UserService service;
    
    public AlterDeletUserController() {
        service = new UserService();
    }
    
    public void initialize() {
        user = (User) Router.getUserData();
        fieldName.setText(user.getName());
        fieldUsername.setText(user.getUsername());
        System.out.println(user.getId());
    }

    public void handleOnActionButtonBtnAlter() {
        var user2 = User.builder()
                .name(fieldName.getText())
                .username(fieldUsername.getText())
                .password(fieldPassword.getText())
                .role(Role.ADMIN)
                .status(Status.ACTIVE)
                .build();

        try {
            if (fieldPassword.getText().equals(fieldConfPassword.getText()) && service.userUpdate(user.getId(), user2)) {
                Router.showPopUp(PopUpAlterSuccessfulController.class, 1);
                Router.goTo(PeopleManagementController.class);
            } else {
                lblMessage.setText("Senhas são divergentes");
            }
        } catch (ConnectionFailureException e) {
            //TODO Criar pop up
        }
    }
    
    public void handleOnActionButtonBtnDelete() {
        try {
            if(service.userDelete(user.getId())) {
                System.out.println("Tudo ok");
            } else {
                System.out.println("Deu ruim");
            }
        } catch (ConnectionFailureException e) {
            //TODO Criar pop up
        }
    }

}
