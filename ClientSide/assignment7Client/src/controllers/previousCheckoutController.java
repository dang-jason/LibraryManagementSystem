package controllers;

import client.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class previousCheckoutController implements Initializable {
    private Client client;
    @FXML
    private Button minimize;
    @FXML
    private TableView<ObservableList<String>> checkoutViewer;
    @FXML
    private TableColumn<ObservableList<String>, String> previousName;
    @FXML
    private TableColumn<ObservableList<String>, String> previousDate;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        previousName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        previousDate.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
    }

    public void setClient(Client client){
        this.client = client;
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        for(int i = 0; i < client.getPreviousDates().size(); i++){
            ObservableList<String> row = FXCollections.observableArrayList();
            row.addAll(client.getPreviousNames().get(i), client.getPreviousDates().get(i));
            data.add(row);
        }
        if(data.size() > 0) {
            checkoutViewer.setItems(data);
            checkoutViewer.refresh();
        }
    }
    @FXML
    public void minimize(){
        Stage stage = (Stage)minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    public void exit(ActionEvent event){
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
