import com.google.gson.Gson;
import data.Item;
import databases.libraryDatabase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;


import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
public class Server extends Observable {
    private ServerSocket serverSocket;
    private static int port = 4242;

    public Server(ServerSocket serverSocket){this.serverSocket = serverSocket;}
    public static void main(String[] args) {
       MongoCollection<Document> collection = libraryDatabase.connectDatabase();
       libraryDatabase.update();
//        try {
//            ServerSocket serverSocket = new ServerSocket(port);
//            Server server = new Server(serverSocket);
//            server.setUpNetworking();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    private void setUpNetworking() {
        try {
            while (!this.serverSocket.isClosed()) {
                Socket clientSocket = this.serverSocket.accept();
                ClientHandler handler = new ClientHandler(this, clientSocket);
                System.out.println("Connecting to... " + clientSocket);
                Thread t = new Thread(handler);
                t.start();
//                this.addObserver(handler);
            }
        }catch(IOException e){
            closeServerSocket();
        }
    }
    protected void processRequest(Item input) {
        String output = "Error";
        System.out.println("Item is being processed in the server");
        try {
            this.setChanged();
            this.notifyObservers(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeServerSocket(){
        try {
            if (serverSocket != null) serverSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}