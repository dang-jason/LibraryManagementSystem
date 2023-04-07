import com.google.gson.Gson;
import data.Item;
import databases.libraryDatabase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class Server extends Observable {
    private ServerSocket serverSocket;
    private static int port = 4242;

    public Server(ServerSocket serverSocket){this.serverSocket = serverSocket;}
    public static void main(String[] args) {
//       libraryDatabase db = new libraryDatabase();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Server server = new Server(serverSocket);
            server.setUpNetworking();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setUpNetworking() {
        try {
            while (!this.serverSocket.isClosed()) {
                Socket clientSocket = this.serverSocket.accept();
                ClientHandler handler = new ClientHandler(this, clientSocket);
                System.out.println("Connecting to... " + clientSocket);
                Thread t = new Thread(handler);
                t.start();
//                handler.run();
//                this.addObserver(handler);
            }
        }catch(IOException e){
            closeServerSocket();
        }
    }
    protected void processRequest(Item input) {
        String output = "Error";
//        Gson gson = new Gson();
//        Item item = gson.fromJson(input, data.Item.class);
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