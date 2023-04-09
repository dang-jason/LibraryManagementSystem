package client;

import data.Item;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends Application {
    private static String host = "127.0.0.1";
    private static int port = 4243;
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    private Socket socket;
    public Client(){
        try {
            setUpNetworking();
        } catch (Exception e) {
            System.err.println("PROBLEM INITIALIZING CLIENT");
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage applicationStage) {
        // TODO: Implement this method
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../fxmls/login.fxml"));

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        applicationStage.setTitle("ECE 422C Library User");
        applicationStage.setScene(new Scene(root));
        applicationStage.show();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void setUpNetworking() throws Exception {
        this.socket = new Socket(host, port);
        System.out.println("Connecting to... " + socket);
        this.toServer = new ObjectOutputStream(socket.getOutputStream());
        this.fromServer = new ObjectInputStream(socket.getInputStream());

//        readFromServer();
//        while(true){}
    }

    public void readFromServer(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                Item itemFromServer;
                while(socket.isConnected()){
                    try {
                        while((itemFromServer = (Item) fromServer.readObject()) != null) {
                            System.out.println("Item received from server:" + itemFromServer);
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("Error in receiving item from server");
                        closeEverything(socket, fromServer, toServer);
                    }
                }
            }
        }).start();
    }

    public void sendToServer(Item item) {
        System.out.println("Sending to server: " + item);
        try {
//            while(socket.isConnected()){
                toServer.reset();
                toServer.writeUnshared(item); // later replace when have gui
                toServer.flush();
//                socket.close();
                System.out.println("Item has been sent to server");
//                break; // might not need to break here if reading inputs?
//            }
        }catch(Exception e){
            System.out.println("Error in sending to server");
            closeEverything(socket, fromServer, toServer);
        }
    }

    public void closeEverything(Socket socket, ObjectInputStream fromServer, ObjectOutputStream toServer){
        try{
            if(fromServer != null) fromServer.close();
            if(toServer != null) toServer.close();
            if(socket != null) socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}