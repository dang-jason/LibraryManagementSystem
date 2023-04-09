package controllers;

import data.Item;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController implements Initializable {
    private Client client;

    @FXML
    private Button loginBtn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button exit;
    @FXML
    private Button minimize;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.client = new Client();
    }
    public void login(){
        Item itemToSend = new Item("Book", "Cant hurt me", "David Goggins", 363, "His Life");
        this.client.sendToServer(itemToSend);
    }

    @FXML
    public void minimize(){
        Stage stage = (Stage)minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    public void exit(){
        System.exit(0);
    }
}
