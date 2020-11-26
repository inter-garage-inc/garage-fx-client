package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.ParkingSpaceRegistration;
import app.data.User;
import app.data.parking.ParkingSpace;
import app.data.parking.SpaceStatus;
import app.data.user.Role;
import app.router.RouteMapping;
import app.router.Router;
import app.service.AuthenticationService;
import app.service.ParkingSpacesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

@RouteMapping(title = "Mapa de Vagas")
public class ParkingSpacesMapController {
    @FXML
    private MainMenuController menuController;

    @FXML
    private GridPane gridPaneMap;

    @FXML
    private Button buttonRegistration;

    private User user;

    private ParkingSpacesService service;

    private List<ParkingSpace> parkingSpaces;

    public ParkingSpacesMapController() {
        user = AuthenticationService.claimUser();
        service = new ParkingSpacesService();
    }

    @FXML
    private void initialize() {
        menuController.btnParkingSpacesMap.getStyleClass().add("button-menu-selected");
        if(user.getRole() == Role.EMPLOYEE) {
            buttonRegistration.setVisible(false);
        }
        loadMap();
    }

    private void loadMap() {
        try {
            parkingSpaces = service.index();
        } catch (ConnectionFailureException exception) {
            exception.printStackTrace();
        }

        gridPaneMap.getChildren().clear();
        parkingSpaces.forEach(ps -> {
            var labelCode = new Label(ps.getCode());
            labelCode.getStyleClass().addAll("parkingSpace", ps.getStatus().name());

            if(user.getRole() != Role.EMPLOYEE && ps.getStatus() != SpaceStatus.OCCUPIED) {
                var menuItemEnableDisable = new MenuItem(
                        ps.getStatus() == SpaceStatus.DISABLED
                            ? "Tornar Ativa"
                            : "Tornar Inativa"
                );
                menuItemEnableDisable.setOnAction(
                        ps.getStatus() == SpaceStatus.DISABLED
                            ? event -> this.handleActivation(ps)
                            : event -> this.handleDeactivation(ps)
                );
                var menuItemDelete = new MenuItem("Excluir vaga");
                menuItemDelete.setOnAction(event -> this.handleDeletion(ps));
                labelCode.setContextMenu(new ContextMenu(menuItemEnableDisable, menuItemDelete));
            }
            gridPaneMap.add(labelCode, ps.getColumnPosition(), ps.getRowPosition());
        });
    }

    @FXML
    private void handleDeactivation(ParkingSpace parkingSpace) {
        var ps = ParkingSpace.builder()
                .code(parkingSpace.getCode())
                .status(SpaceStatus.DISABLED)
                .build();
        try {
            service.update(parkingSpace.getId(), ps);
            loadMap();
        } catch (ConnectionFailureException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void handleActivation(ParkingSpace parkingSpace) {
        var ps = ParkingSpace.builder()
                .code(parkingSpace.getCode())
                .status(SpaceStatus.VACANT)
                .build();
        try {
            service.update(parkingSpace.getId(), ps);
            loadMap();
        } catch (ConnectionFailureException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void handleDeletion(ParkingSpace parkingSpace) {
        try {
            service.delete(parkingSpace.getId());
            loadMap();
        } catch (ConnectionFailureException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void handleRegistration(ActionEvent actionEvent) {
        Router.showPopUp(ParkingSpaceRegistration.class);
    }
}
