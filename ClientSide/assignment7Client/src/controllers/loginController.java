package controllers;

import client.Client;
import com.mongodb.BasicDBObject;
import data.Item;
import databases.userDatabase;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class loginController implements Initializable {
    private Client client;

    @FXML
    private Button loginBtn;
    @FXML
    private Button registerBtn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.client = new Client(0);
    }
    public void login(ActionEvent event){
        MongoCollection<Document> collection = userDatabase.getCollection();
        try {
            Alert alert;
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields.");
                alert.showAndWait();
            } else {
                Document doc = collection.find(Filters.and(Filters.eq("username", username.getText().toLowerCase()), Filters.eq("password", password.getText()))).first();
                if(doc != null){
                    this.client.setUsername(username.getText());

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Admin Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    //show dashboard
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxmls/dashboard.fxml"));
                    Parent root = loader.load();
                    dashboardController dashboardController = loader.getController();
                    dashboardController.setClient(this.client);
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Admin Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid username or password. Please try again.");
                    alert.showAndWait();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("Error in login");
        }
    }
    @FXML
    public void register() {
        MongoCollection<Document> collection = userDatabase.getCollection();
        try {
            Alert alert;
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields.");
                alert.showAndWait();
            } else {
                Document doc = collection.find(Filters.eq("username", username.getText().toLowerCase())).first();
                if(doc == null){
                    Document profile = new Document();
                    profile.put("username", username.getText().toLowerCase());
                    profile.put("password", password.getText());
                    collection.insertOne(profile);
                    username.clear();
                    password.clear();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Admin Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Registration successful. Please login to proceed.");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Admin Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Sorry, this username is already taken. Please choose another username.");
                    alert.showAndWait();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("Error in registering");
        }
    }
}
