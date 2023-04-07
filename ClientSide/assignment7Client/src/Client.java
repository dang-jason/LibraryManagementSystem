import com.google.gson.GsonBuilder;
import data.Item;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private static String host = "127.0.0.1";
    private static int port = 4243;
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;
    private Socket socket;
    public static void main(String[] args) {
        try {
            new Client().setUpNetworking();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setUpNetworking() throws Exception {
        this.socket = new Socket(host, port);
        System.out.println("Connecting to... " + socket);
        this.toServer = new ObjectOutputStream(socket.getOutputStream());
        this.fromServer = new ObjectInputStream(socket.getInputStream());
        Item itemToSend = new Item("Book", "Cant hurt me", "David Goggins", 363, "His Life");
        sendToServer(itemToSend);
//        readFromServer();
        while(true){}
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