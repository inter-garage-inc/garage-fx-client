package app.controller;

import app.data.Customer;
import app.router.RouteMapping;
import app.service.ConnectionFailureException;
import app.service.CustomerService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@RouteMapping(title = "Novo Cliente")
public class RegisterCustomerController extends ApplicationController {

    @FXML
    private TextField fieldName;

    @FXML
    private TextField fieldCpfCnpj;

    @FXML
    private TextField fieldPhone;

    @FXML
    public void register() {
        var customer = Customer
                .builder()
                .name(fieldName.getText())
                .cpfCnpj(Integer.parseInt(fieldCpfCnpj.getText()))
                .phone(Integer.parseInt(fieldPhone.getText()))
                .build();
        var service = new CustomerService();
        try {
            var response = service.save(customer);
            if (response) {
                System.out.println("Sucesso");
            } else {
                System.out.println("Não foi possivel cadastrar");
            }
        } catch (ConnectionFailureException e) {
            System.out.println("Não foi possivel comunicar com o back-end ");
        }
    }

}
