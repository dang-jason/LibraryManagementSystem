/*
 * EE422C Final Project submission by
 * <Jason Dang>
 * <jd52753>
 * <17185>
 * Spring 2023
 */
package controllers;

import client.Client;
import data.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class holderController implements Initializable {
    private Client client;
    Item item;
    @FXML
    private Button minimize;
    @FXML
    private TableView<ObservableList<String>> holdViewer;
    @FXML
    private TableColumn<ObservableList<String>, String> holderName;
    @FXML
    private TableColumn<ObservableList<String>, String> position;
    private int i;
    ObservableList<ObservableList<String>> data;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        holderName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(0)));
        position.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(1)));
        holdViewer.setPlaceholder(new Label("This item is currently not reserved"));
    }

    public void setClient(Client client){
        this.client = client;
        data = FXCollections.observableArrayList();
        if(!item.getHolders().equals("")) {
            for (i = 0; i < item.getHolders().split(",").length; i++) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.addAll(item.getHolders().split(", ")[i], Integer.toString(i+1));
                data.add(row);
            }
            holdViewer.setItems(data);
        }
        holdViewer.refresh();
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @FXML
    public void holdItem(){
        if(item.getCurrent().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Admin Message");
            alert.setHeaderText(null);
            alert.setContentText("There is currently noone checking out this book, so you cannot place a hold");
            alert.showAndWait();
            return;
        }
        if(item.getCurrent().equals(client.getUsername())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Admin Message");
            alert.setHeaderText(null);
            alert.setContentText("You currently own the book you can not place a hold");
            alert.showAndWait();
            return;
        }
        for(ObservableList<String> d: data){
            if(d.get(0).equals(client.getUsername())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("You are already at position " + d.get(1) + " in the hold");
                alert.showAndWait();
                return;
            }
        }
        String s = item.getHolders();
        s = s.length() != 0 ? (s += ", " + client.getUsername()) : client.getUsername();
        item.setHolders(s);
        client.sendToServer("hold", item);
        ObservableList<String> row = FXCollections.observableArrayList();
        row.addAll(client.getPreviousNames().get(i), Integer.toString(i+1));
        i++;
        data.add(row);
        holdViewer.refresh();
    }
    @FXML
    public void cancelHold(){
        ArrayList<String> s = new ArrayList<String>(Arrays.asList(item.getHolders().split(", ")));
        if(!s.contains(client.getUsername())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Admin Message");
            alert.setHeaderText(null);
            alert.setContentText("You currently do not have a hold on this item");
            alert.showAndWait();
            return;
        }
        s.remove(client.getUsername());
        item.setHolders(String.join(", ", s));
        client.sendToServer("hold", item);
        data = FXCollections.observableArrayList();
        if(!item.getHolders().equals("")) {
            for (i = 0; i < s.size(); i++) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.addAll(item.getHolders().split(", ")[i], Integer.toString(i + 1));
                data.add(row);
            }
            if (data.size() > 0) {
                holdViewer.setItems(data);
            }
        }
        holdViewer.refresh();
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
