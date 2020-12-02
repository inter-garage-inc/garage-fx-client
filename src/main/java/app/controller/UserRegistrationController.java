package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpRegisterSuccessfulController;
import app.controller.popup.PopUpServerCloseController;
import app.data.User;
import app.data.user.Role;
import app.data.user.Status;
import app.router.RouteMapping;
import app.router.Router;
import app.service.AuthenticationService;
import app.service.UsersService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

@RouteMapping(title = "Cadastro de Usuário")
public class UserRegistrationController {

    public TextField fieldName;
    public TextField fieldUsername;
    public PasswordField fieldPassword;
    public PasswordField fieldConfPassword;
    public Button btnSave;
    public Label lblMessage;
    public AnchorPane anchorPane;
    public ComboBox<Role> cbStatus;
    public Label lblTypeUser;
    private AuthenticationService authenticationService;
    private Boolean authenticationUser;
    private UsersService service;

    public UserRegistrationController() {
        service = new UsersService();
        anchorPane = new AnchorPane();
        authenticationService = new AuthenticationService();
    }

    @FXML
    private MainMenuController menuController;
    public void initialize() {
        menuController.btnUsers.getStyleClass().add("button-menu-selected");
        authenticationUser = authenticationService.claimUser().getRole().equals(Role.ADMIN);
        if(authenticationUser) {
            anchorPane.setVisible(true);
            cbStatus.getItems().addAll(Role.values());
        } else {
            anchorPane.setVisible(false);
            cbStatus.setVisible(false);
            lblTypeUser.setVisible(false);
        }
    }

    public void handleOnActionButtonBtnSave() {
        Boolean confirmPassword = fieldPassword.getText().equals(fieldConfPassword.getText());
        Boolean nullName = fieldName.getText() == null || fieldName.getText().trim().isEmpty();
        Boolean nullUsername = fieldUsername.getText() == null || fieldUsername.getText().trim().isEmpty();
        Boolean nullPassword = fieldPassword.getText() == null || fieldPassword.getText().trim().isEmpty();
        Boolean nullConfirmPassword = fieldConfPassword.getText() == null || fieldConfPassword.getText().trim().isEmpty();

        if(nullName || nullUsername || nullPassword || nullConfirmPassword) {
            lblMessage.setText("Os campos não podem ser vazios");
            return;
        }
        User user = null;
        if(authenticationUser) {
            user = User.builder()
                    .name(fieldName.getText())
                    .username(fieldUsername.getText())
                    .password(fieldPassword.getText())
                    .role(cbStatus.getValue())
                    .status(Status.ACTIVE)
                    .build();
        } else {
            user = User.builder()
                    .name(fieldName.getText())
                    .username(fieldUsername.getText())
                    .password(fieldPassword.getText())
                    .role(Role.EMPLOYEE)
                    .status(Status.ACTIVE)
                    .build();
        }

        if(!confirmPassword) {
            lblMessage.setText("Senhas não são iguais");
            fieldPassword.setText("");
            fieldConfPassword.setText("");
            return;
        }

        try {
            if(service.userSave(user)) {
                Router.showPopUp(PopUpRegisterSuccessfulController.class, 1);
                Router.goTo(PeopleManagementController.class);
                lblMessage.setVisible(false);
            } else {
                lblMessage.setText("Username ja existente");
                fieldUsername.setText("");
            }

        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }
}
