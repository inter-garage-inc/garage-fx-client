package app.controller;

import app.client.ConnectionFailureException;
import app.controller.popup.PopUpDeleteSuccessController;
import app.data.Catalog;
import app.data.Plan;
import app.data.plan.Status;
import app.data.plan.Type;
import app.router.RouteMapping;
import app.router.Router;
import app.service.CatalogService;
import app.service.PlanService;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RouteMapping(title = "Alteração de Planos")
public class PlanChangeController {
    private Plan plan;
    public ComboBox<Type> cbType;
    public TextField fieldPrice;
    public ComboBox<Status> cbStatus;
    public List<CheckBox> cbCatalogs = new ArrayList<>();
    public AnchorPane anchorPane;

    private CatalogService catalogService;
    private PlanService service;

    public void initialize() throws ConnectionFailureException {
        var data = (Plan)Router.getUserData();
        this.service = new PlanService();

        this.plan  = service.findById(data.getId());

        cbType.getItems().addAll(Type.values());
        cbType.setValue(this.plan.getType());
        fieldPrice.setText(this.plan.getPrice().toString());
        cbStatus.getItems().addAll(Status.values());
        cbStatus.setValue(this.plan.getStatus());

        fillCatalogs();
        selectedCatalogs();
    }

    private List<Catalog> catalogs() throws ConnectionFailureException {
        this.catalogService = new CatalogService();
        return catalogService.CatalogFindAll();
    }

    protected void fillCatalogs() {
        CheckBox checkBox;
        int y = 10;
        try {
            var catalogs = catalogs();
            for (Catalog c : catalogs) {
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

    protected void selectedCatalogs() {
        var panes = anchorPane.getChildren();
        var catalogs = this.plan.getCatalog();
        for(Node node : panes){
            CheckBox checkBox = (CheckBox) node;
            for(Catalog c : catalogs){
               if(Long.valueOf(checkBox.getId()) == c.getId()) {
                   checkBox.setSelected(true);
               }
            }
        }
    }

    public void handleOnActionButtonBtnAlter() throws ConnectionFailureException {
        var panes = anchorPane.getChildren();
        List<Catalog> catalogs = new ArrayList<>();
        this.catalogService = new CatalogService();

        for(Node node : panes){
            CheckBox checkBox = (CheckBox) node;
            if(checkBox.isSelected()){
                Catalog catalog = catalogService.findBy(Long.valueOf(checkBox.getId()));
                catalog.setCreatedAt(null);
                catalog.setUpdatedAt(null);
                catalogs.add(catalog);
            }
        }

        try {
            var plan = Plan.builder()
                    .name(catalogs.stream().map(c -> c.getDescription()).collect(Collectors.joining(" ")))
                    .type(cbType.getValue())
                    .price(new BigDecimal(fieldPrice.getText()))
                    .status(cbStatus.getValue())
                    .catalog(catalogs)
                    .build();

            var service = new PlanService();
            plan.setId(this.plan.getId());
            service.update(plan, this.plan.getId());
            Router.goTo(PlanManagementController.class);
        } catch (ConnectionFailureException connectionFailureException) {
            connectionFailureException.printStackTrace();
        }
    }

    public void handleOnActionButtonBtnDelete() {
        try {
            var service = new PlanService();
            service.delete(this.plan.getId());

            Router.showPopUp(PopUpDeleteSuccessController.class, 1);
            Router.goTo(PlanManagementController.class);
        } catch (ConnectionFailureException e) {
        }
    }
}
