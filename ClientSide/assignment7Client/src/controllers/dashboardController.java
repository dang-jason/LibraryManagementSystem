package controllers;

import client.Client;
import data.Item;
import databases.userDatabase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {
    private Client client;
    @FXML
    private Button minimize;
    @FXML
    private Label userTag;
    @FXML
    private ImageView imageDisplay;
    @FXML
    private TableView<Item> bookTable;
    @FXML
    private TableColumn<Item, String> bookName;
    @FXML
    private TableColumn<Item, String> bookAuthor;
    @FXML
    private TableColumn<Item, Integer> bookPages;
    @FXML
    private TableColumn<Item, String> bookSummary;
    @FXML
    private TableColumn<Item, String> bookCheckout;

    @FXML
    private TableView<Item> gameTable;
    @FXML
    private TableColumn<Item, String> gameName;
    @FXML
    private TableColumn<Item, String> gameDeveloper;
    @FXML
    private TableColumn<Item, Integer> gameYear;
    @FXML
    private TableColumn<Item, String> gameSummary;
    @FXML
    private TableColumn<Item, String> gameCheckout;

    @FXML
    private TableView<Item> checkoutTable;
    @FXML
    private TableColumn<Item, String> itemCheckoutName;
    @FXML
    private TableColumn<Item, String> itemCheckoutType;
    @FXML
    private TableColumn<Item, String> itemDateCheckout;
    @FXML
    private TableColumn<Item, String> itemReturnDate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        gameTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        checkoutTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        bookName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        bookName.setCellFactory(tc -> {
            TableCell<Item, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(bookName.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        bookAuthor.setCellValueFactory(new PropertyValueFactory<Item, String>("creator"));
        bookAuthor.setCellFactory(tc -> {
            TableCell<Item, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(bookAuthor.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        bookPages.setCellValueFactory(new PropertyValueFactory<Item, Integer>("pages_year"));
        bookSummary.setCellValueFactory(new PropertyValueFactory<Item, String>("summary"));
        bookSummary.setCellFactory(tc -> {
            TableCell<Item, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(bookSummary.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        bookCheckout.setCellValueFactory(new PropertyValueFactory<Item, String>("current"));
        gameName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        gameName.setCellFactory(tc -> {
            TableCell<Item, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(gameName.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        gameYear.setCellValueFactory(new PropertyValueFactory<Item, Integer>("pages_year"));
        gameDeveloper.setCellValueFactory(new PropertyValueFactory<Item, String>("creator"));
        gameDeveloper.setCellFactory(tc -> {
            TableCell<Item, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(gameDeveloper.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        gameSummary.setCellValueFactory(new PropertyValueFactory<Item, String>("summary"));
        gameSummary.setCellFactory(tc -> {
            TableCell<Item, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(gameSummary.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        gameCheckout.setCellValueFactory(new PropertyValueFactory<Item, String>("current"));
        itemCheckoutName.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
        itemCheckoutName.setCellFactory(tc -> {
            TableCell<Item, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(itemCheckoutName.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        itemCheckoutType.setCellValueFactory(new PropertyValueFactory<Item, String>("itemType"));
//        itemDateCheckout.setCellValueFactory(new PropertyValueFactory<Item, String>(""));
//        itemReturnDate.setCellValueFactory(new PropertyValueFactory<Item, String>(""));
    }

    public void setClient(Client client){this.client = client;}
    public void setItems(){
        bookTable.setItems(client.getBooks());
        gameTable.setItems(client.getGames());
        checkoutTable.setItems(client.getCheckout());
        refreshTable();
    }
    public void setUser(){
        userTag.setText("User: " + client.getUsername());
    }
    public void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxmls/logins.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML void getItem(MouseEvent event){
        Item item = null;
        if(((TableView)event.getSource()).getId().equals("bookTable")){
            item = bookTable.getSelectionModel().getSelectedItem();
            if(item == null) return;
        }else if(((TableView)event.getSource()).getId().equals("gameTable")){
            item = gameTable.getSelectionModel().getSelectedItem();
            if(item == null) return;
        }else if(((TableView)event.getSource()).getId().equals("checkoutTable")){
            item = checkoutTable.getSelectionModel().getSelectedItem();
            if(item == null) return;
        }
        if(item != null && userDatabase.getImage(item.getName()) != null) {
            InputStream inputStream = new ByteArrayInputStream(userDatabase.getImage(item.getName()));
            Image image = new Image(inputStream);
            imageDisplay.setImage(image);
        }
    }
    @FXML
    public void checkoutItem(){
        ObservableList<Item> books = bookTable.getSelectionModel().getSelectedItems();
        Alert alert;
        for(Item i : books) {
            if(i.getCurrent().equals("")) {
                i.setCurrent(client.getUsername());
                bookTable.refresh();
                client.getCheckout().add(i);
                checkoutTable.refresh();
                client.sendToServer("checkout", i);
            }
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                if(i.getCurrent().equals(client.getUsername())){
                 alert.setContentText("Sorry, you are already checking out " + i.getName());
                }else {
                    alert.setContentText("Sorry " + i.getName() + " is already checked out by " + i.getCurrent());
                }
                alert.showAndWait();
            }
        }
        bookTable.getSelectionModel().clearSelection();
        ObservableList<Item> games = gameTable.getSelectionModel().getSelectedItems();
        for(Item i : games) {
            if(i.getCurrent().equals("")) {
                i.setCurrent(client.getUsername());
                gameTable.refresh();
                client.getCheckout().add(i);
                checkoutTable.refresh();
                client.sendToServer("checkout", i);
            }
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Sorry " + i.getName() + " is already checked out by " + i.getCurrent());
                alert.showAndWait();
            }
        }
        gameTable.getSelectionModel().clearSelection();
        ObservableList<Item> checkout = checkoutTable.getSelectionModel().getSelectedItems();
        if(checkout.size() > 0){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Admin Message");
            alert.setHeaderText(null);
            alert.setContentText("Sorry these are items you currently have checked out. Do you mean to return?");
            alert.showAndWait();
        }
        checkoutTable.getSelectionModel().clearSelection();
    }
    @FXML
    public void returnItem(){
        ObservableList<Item> books = bookTable.getSelectionModel().getSelectedItems();
        Alert alert;
        for(Item i : books) {
            if(i.getCurrent().equals(client.getUsername())) {
                String s = i.getPrevious();
                if(s.length() != 0){
                    s += ", " + i.getCurrent();
                }else{
                    s = i.getCurrent();
                }
                i.setCurrent("");
                bookTable.refresh();
                client.getCheckout().remove(i);
                checkoutTable.refresh();
                client.sendToServer("return", i);
            }
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Sorry, your book" + i.getName() + " does not belong to us.");
                alert.showAndWait();
            }
        }
        bookTable.getSelectionModel().clearSelection();
        ObservableList<Item> games = gameTable.getSelectionModel().getSelectedItems();
        for(Item i : games) {
            String s = i.getPrevious();
            if(s.length() != 0){
                s += ", " + i.getCurrent();
            }else{
                s = i.getCurrent();
            }
            if(i.getCurrent().equals(client.getUsername())) {
                i.setCurrent("");
                gameTable.refresh();
                client.getCheckout().remove(i);
                checkoutTable.refresh();
                client.sendToServer("return", i);
            }
            else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Sorry, it is not possible for you to return a book you do not currently own.");
                alert.showAndWait();
            }
        }
        gameTable.getSelectionModel().clearSelection();
        ObservableList<Item> checkout = checkoutTable.getSelectionModel().getSelectedItems();
        for(Item i : checkout) {
            String s = i.getPrevious();
            if(s.length() != 0){
                s += ", " + i.getCurrent();
            }else{
                s = i.getCurrent();
            }
            i.setCurrent("");
            bookTable.refresh();
            gameTable.refresh();
            client.getCheckout().remove(i);
            checkoutTable.refresh();
            client.sendToServer("return", i);
        }
        checkoutTable.getSelectionModel().clearSelection();
    }
    public void refreshTable(){
        bookTable.refresh();
        gameTable.refresh();
        checkoutTable.refresh();
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
