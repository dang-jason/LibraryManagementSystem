/*
 * EE422C Final Project submission by
 * <Jason Dang>
 * <jd52753>
 * <17185>
 * Spring 2023
 */

package client;

import controllers.dashboardController;
import data.Item;

import databases.userDatabase;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Application {
    private static String host = "127.0.0.1";
    private static int port = 4242;
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    private Socket socket;
    private String username;
    private double x = 0;
    private double y = 0;
    private ObservableList<Item> books = FXCollections.observableArrayList();
    private ObservableList<Item> games = FXCollections.observableArrayList();
    private ObservableList<Item> checkout = FXCollections.observableArrayList();
    private ObservableList<String> previousNames = FXCollections.observableArrayList();
    private ObservableList<String> previousDates = FXCollections.observableArrayList();
    private dashboardController dbController;
    public static Object o = new Object();
    public Client(){}
    public Client(int empty){
        userDatabase.connectDatabase();
        try {
            setUpNetworking();
        } catch (Exception e) {
            System.err.println("PROBLEM INITIALIZING CLIENT");
            e.printStackTrace();
        }
    }
    public String getUsername() {
        return username;
    }
    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage applicationStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../fxmls/logins.fxml"));
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
//        applicationStage.initStyle(StageStyle.UNDECORATED);
        applicationStage.setTitle("ECE 422C Library User");
        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) ->{
            applicationStage.setX(event.getScreenX() - x);
            applicationStage.setY(event.getScreenY() - y);
        });
        applicationStage.setX(390);
        applicationStage.setY(155);
        applicationStage.initStyle(StageStyle.TRANSPARENT);
        applicationStage.setScene(new Scene(root));
        applicationStage.show();
    }

    private void setUpNetworking() throws Exception {
        this.socket = new Socket(host, port);
        System.out.println("Connecting to... " + socket);
        this.toServer = new ObjectOutputStream(socket.getOutputStream());
        this.fromServer = new ObjectInputStream(socket.getInputStream());
    }

    public void readFromServer(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String query;
                Item itemFromServer;
                try {
                    while(!socket.isClosed()){
                        while((query = (String) fromServer.readObject()) != null) {
                            itemFromServer = (Item) fromServer.readObject();
                            if(query.equals("add")){
                                if(itemFromServer.getItemType().equals("book")){
                                    itemFromServer.setItemType("Book");
                                    books.add(itemFromServer);
                                }
                                else{
                                    itemFromServer.setItemType("Game");
                                    games.add(itemFromServer);
                                }
                                if(itemFromServer.getCurrent().equals(username)){
                                    checkout.add(itemFromServer);
                                }
                            }else if(query.equals("update")) {
                                if (!itemFromServer.getCurrent().equals(username)) {
                                    if (itemFromServer.getItemType().equals("Book")) {
                                        for (Item i : books) {
                                            if (i.getName().equals(itemFromServer.getName())) {
                                                i.setCurrent(itemFromServer.getCurrent());
                                                dbController.refreshTable();
                                                break;
                                            }
                                        }
                                    } else {
                                        itemFromServer.setItemType("Game");
                                        for (Item i : games) {
                                            if (i.getName().equals(itemFromServer.getName())) {
                                                i.setCurrent(itemFromServer.getCurrent());
                                                dbController.refreshTable();
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error in receiving item from server");
                    closeEverything();
                }
            }
        }).start();
    }
    public void sendToServer(String query, Item item) {
        try {
                toServer.reset();
                toServer.writeUnshared(query);
                toServer.flush();
                if(item != null) {
                    toServer.reset();
                    toServer.writeUnshared(item);
                    toServer.flush();
                }
        }catch(Exception e){
            System.out.println("Error in sending to server");
            closeEverything();
        }
    }

    public void closeEverything(){
        try{
            if(fromServer != null) fromServer.close();
            if(toServer != null) toServer.close();
            if(socket != null) socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setUsername(String username){this.username = username;}
    public void setDbController(dashboardController dbController) {
        this.dbController = dbController;
    }
    public ObservableList<Item> getBooks() {
        return books;
    }

    public ObservableList<Item> getGames() {
        return games;
    }

    public ObservableList<Item> getCheckout() {
        return checkout;
    }

    public ObservableList<String> getPreviousNames() {
        return previousNames;
    }

    public ObservableList<String> getPreviousDates() {
        return previousDates;
    }

    public void setPreviousNames(ObservableList<String> previousNames) {
        this.previousNames = previousNames;
    }

    public void setPreviousDates(ObservableList<String> previousDates) {
        this.previousDates = previousDates;
    }
}