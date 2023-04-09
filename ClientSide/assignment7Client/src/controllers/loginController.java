package controllers;

import client.Client;
import data.Item;
import databases.userDatabase;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    public void login(){
        String user = username.getText();
        String pw = password.getText();
    }
    @FXML
    public void register() {
        MongoCollection<Document> collection = userDatabase.connectDatabase();
        try {
            Alert alert;
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields.");
                alert.showAndWait();
            } else {
                MongoCursor cursor = collection.find(Filters.empty()).cursor();
            }
        }catch(Exception e){
            e.printStackTrace();
            System.err.println("Error in registering");
        }
    }
}
