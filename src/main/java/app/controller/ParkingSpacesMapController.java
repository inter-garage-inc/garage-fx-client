package app.controller;

import app.client.ConnectionFailureException;
import app.controller.component.MainMenuController;
import app.controller.popup.PopUpParkingSpaceRegistration;
import app.controller.popup.PopUpServerCloseController;
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
import javafx.scene.layout.GridPane;
import java.util.List;

/**
 * @author jlucasrods
 * @version 1.0
 * @since 2020-12-01
 */

@RouteMapping(title = "Mapa de Vagas")
public class ParkingSpacesMapController {

    @FXML
    private GridPane gridPaneMap;

    @FXML
    private Button buttonRegistration;

    private MainMenuController menuController;

    private User user;

    private ParkingSpacesService service;

    private List<ParkingSpace> parkingSpaces;

    public ParkingSpacesMapController() {
        user = AuthenticationService.claimUser();
        service = new ParkingSpacesService();
    }

    /**
     * The initialize Method cancel the access the {@link app.data.user.Role} EMPLOYEE on change buttons
     */
    public void initialize() {
        menuController.btnParkingSpacesMap.getStyleClass().add("button-menu-selected");
        if(user.getRole() == Role.EMPLOYEE) {
            buttonRegistration.setVisible(false);
        }
        loadMap();
    }

    /**
     * This method show the map using the service {@link ParkingSpacesService}
     */
    public void loadMap() {
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

    /**
     * This method disable the parking space from map using {@link ParkingSpacesService}
     * @param parkingSpace parking space to be disable
     */
    public void handleDeactivation(ParkingSpace parkingSpace) {
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

    /**
     * This method active the parking space from map using {@link ParkingSpacesService}
     * @param parkingSpace parking space to be active
     */
    public void handleActivation(ParkingSpace parkingSpace) {
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

    /**
     * This method delete the parking space from map using {@link ParkingSpacesService}
     * @param parkingSpace parking space to be deleted
     */
    public void handleDeletion(ParkingSpace parkingSpace) {
        try {
            service.delete(parkingSpace.getId());
            loadMap();
        } catch (ConnectionFailureException e) {
            Router.showPopUp(PopUpServerCloseController.class, 2);
        }
    }

    /**
     * This method call {@link PopUpParkingSpaceRegistration} using {@link Router}
     */
    public void handleRegistration() {
        Router.showPopUp(PopUpParkingSpaceRegistration.class);
    }
}
