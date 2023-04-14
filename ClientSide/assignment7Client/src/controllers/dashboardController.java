package controllers;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {
    private Client client;
    @FXML
    private Button exit;
    @FXML
    private Button minimize;
    @Override
    public void initialize(URL location, ResourceBundle resources) {}
    public void setClient(Client client){this.client = client;}
    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxmls/logins.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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