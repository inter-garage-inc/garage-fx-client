package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpChangeSuccessfulController;
import app.controller.popup.PopUpConfirmDeleteUserController;
import app.controller.popup.PopUpServerCloseController;
import app.data.User;
import app.data.user.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.UsersService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

@RouteMapping(title = "Alterar/Deletar usuário")
public class AlterDeletUserController {

    public TextField fieldName;
    public TextField fieldUsername;
    public PasswordField fieldPassword;
    public PasswordField fieldConfPassword;
    public Button btnAlter;
    public Label lblMessage;
    public Button btnDelete;
    public ComboBox<Status> cbStatus;
    User user;
    UsersService service;
    @FXML
    private MainMenuController menuController;
    
    public AlterDeletUserController() {
        service = new UsersService();
    }
    
    public void initialize() {
        menuController.btnUsers.getStyleClass().add("button-menu-selected");
        user = (User) Router.getUserData();

        fieldName.setText(user.getName());
        fieldUsername.setText(user.getUsername());
        cbStatus.getItems().addAll(Status.values());
        cbStatus.setValue(user.getStatus());
    }

    public void handleOnActionButtonBtnAlter() {
        Boolean confirmPassword = fieldPassword.getText().equals(fieldConfPassword.getText());
        Boolean nullName = fieldName.getText() == null || fieldName.getText().trim().isEmpty();
        Boolean nullUsername = fieldUsername.getText() == null || fieldUsername.getText().trim().isEmpty();
        Boolean nullPassword = fieldPassword.getText() == null || fieldPassword.getText().trim().isEmpty();
        Boolean nullConfirmPassword = fieldConfPassword.getText() == null || fieldConfPassword.getText().trim().isEmpty();

        if(nullName || nullUsername || nullPassword || nullConfirmPassword) {
            lblMessage.setText("Os campos não podem ser vazios");
            return;
        }

        var userAlter = User.builder()
                .name(fieldName.getText())
                .username(fieldUsername.getText())
                .password(fieldPassword.getText())
                .role(user.getRole())
                .status(cbStatus.getValue())
                .build();

        if (!confirmPassword) {
            lblMessage.setText("Senhas não são iguais");
            fieldPassword.setText("");
            fieldConfPassword.setText("");
            return;
        }

        try {
            if(service.userUpdate(user.getId(), userAlter)) {
                Router.showPopUp(PopUpChangeSuccessfulController.class, 1);
                Router.goTo(PeopleManagementController.class);
            } else {
                lblMessage.setText("Não foi possível realizar a alteração");
            }

        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }
    
    public void handleOnActionButtonBtnDelete() { //TODO
        Router.showPopUp(PopUpConfirmDeleteUserController.class, user);
    }

}
