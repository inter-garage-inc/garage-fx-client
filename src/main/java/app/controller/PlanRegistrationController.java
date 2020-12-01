package app.controller;

import app.client.ConnectionFailureException;
import app.controller.popup.PopUpRegisterSuccessfulController;
import app.data.Catalog;
import app.data.Plan;
import app.data.plan.Status;
import app.data.plan.Type;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogsService;
import app.service.PlanService;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RouteMapping(title = "Cadastro de Planos")
public class PlanRegistrationController {

    public ComboBox<Type> cbType;
    public TextField fieldPrice;
    public ComboBox<Status> cbStatus;
    public Button btnSave;
    public List<CheckBox> cbCatalogs = new ArrayList<>();
    public AnchorPane anchorPane;

    private CatalogsService service;

    public void initialize() {
        fillStatus();
        fillType();
        fillCatalogs();
    }

    protected void fillStatus() {
        cbStatus.getItems().addAll(Status.values());
    }

    protected void fillType() {
        cbType.getItems().addAll(Type.values());
    }

    private List<Catalog> catalogs() throws ConnectionFailureException {
        this.service = new CatalogsService();
        return service.CatalogFindAll();
    }

    protected void fillCatalogs(){
        CheckBox checkBox;
        int y = 10;
        try {
            var catalogs = catalogs();
            for(Catalog c : catalogs) {
                checkBox = new CheckBox(c.getDescription());
                checkBox.setId(c.getId().toString());
                checkBox.setLayoutX(15);
                checkBox.setLayoutY(y);
                checkBox.setUserData(c.getDescription());
                cbCatalogs.add(checkBox);
                y = y + 25;
            }
            anchorPane.getChildren().addAll(cbCatalogs);
        } catch (ConnectionFailureException e) {
            e.printStackTrace();
        }
    }

    public void handleOnActionButtonBtnSave() throws ConnectionFailureException {
        var panes = anchorPane.getChildren();
        List<Catalog> catalogs = new ArrayList<>();
        this.service = new CatalogsService();

        for(Node node : panes){
            CheckBox checkBox = (CheckBox) node;
            if(checkBox.isSelected()){
                Catalog catalog = service.findBy(Long.valueOf(checkBox.getId()));
                catalog.setCreatedAt(null);
                catalog.setUpdatedAt(null);
                catalogs.add(catalog);
            }
        }

        var plan = Plan.builder()
                .name(catalogs.stream().map(c -> c.getDescription()).collect(Collectors.joining(" ")))
                .type(cbType.getValue())
                .price(new BigDecimal(fieldPrice.getText()))
                .status(cbStatus.getValue())
                .catalog(catalogs)
                .build();
        try {
            var service = new PlanService();
            var result = service.create(plan);
            if(result) {
                Router.showPopUp(PopUpRegisterSuccessfulController.class, 1);
            } else {
                System.out.println("Não foi possível realizar o cadastro");
            }
            Router.goTo(PlanManagementController.class);
        } catch (ConnectionFailureException e) {
            e.printStackTrace();
        }
    }


}
