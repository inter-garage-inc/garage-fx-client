package app.controller;

import app.data.Customer;
import app.router.RouteMapping;
import app.service.CustomerService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;

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
        var response = service.register(customer);
        if (response == Boolean.TRUE) {
            System.out.println("Sucesso");
        } else if(response == Boolean.FALSE) {
            System.out.println("Não foi possivel cadastrar");
        } else {
            System.out.println("Não foi possivel comunicar com o back-end ");
        }
    }

}
