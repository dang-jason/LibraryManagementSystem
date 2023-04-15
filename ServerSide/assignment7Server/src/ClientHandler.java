import com.mongodb.client.model.Filters;
import data.Item;
import com.google.gson.Gson;
import databases.libraryDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

class ClientHandler implements Runnable, Observer {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Server server; // why do i need this
    private Socket socket;
    private ObjectInputStream fromClient;
    private ObjectOutputStream toClient;

    protected ClientHandler(Server server, Socket clientSocket) {
        this.server = server;
        try {
            this.socket = clientSocket;
            toClient = new ObjectOutputStream(this.socket.getOutputStream());
            fromClient = new ObjectInputStream(this.socket.getInputStream());
            clientHandlers.add(this);
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything(this.socket, this.fromClient, this.toClient);
        }
    }

    protected void sendToClient(String query, Item item) {
        try {
            if(query.equals("add")) {
                toClient.reset();
                toClient.writeUnshared("add");
                toClient.flush();
                toClient.reset();
                toClient.writeUnshared(item);
                toClient.flush();
            }else if(query.equals("update")){
                toClient.reset();
                toClient.writeUnshared("update");
                toClient.flush();
                toClient.reset();
                toClient.writeUnshared(item);
                toClient.flush();
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error in sending to client");
        }
    }
    public void sendCatalog(){
        ArrayList<Item> items = libraryDatabase.parseDB();
        for(Item i : items) sendToClient("add", i);
    }
    @Override
    public void run() {
        String query;
        Item item;
        sendCatalog();
        while(socket.isConnected()){
            try{
                //need to fix read from server if nothing to read -- do something with process request for updating
                while((query = (String) fromClient.readObject()) != null) {
                    item = (Item) fromClient.readObject();
                    if(query.equals("checkout")){
                        server.processRequest(item);
                        Document doc = libraryDatabase.getCollection().find(Filters.eq("name", item.getName())).first();
                        libraryDatabase.getCollection().updateOne(doc, new Document("$set", new Document("currentCheckout", item.getCurrent())));
                    }else if(query.equals("return")){
                        server.processRequest(item);
                        Document doc = libraryDatabase.getCollection().find(Filters.eq("name", item.getName())).first();
                        libraryDatabase.getCollection().updateOne(doc, new Document("$set", new Document("previousCheckouts", item.getPrevious())));
                        libraryDatabase.getCollection().updateOne(doc, new Document("$set", new Document("currentCheckout", item.getCurrent())));
                    }
                }
            } catch(IOException | ClassNotFoundException e){
                System.err.println("Error in receiving item from client");
                e.printStackTrace();
                closeEverything(this.socket, this.fromClient, this.toClient);
                break;
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.sendToClient("update",(Item) arg);
    }

    public void closeEverything(Socket socket, ObjectInputStream fromClient, ObjectOutputStream toClient){
        try{
            if(fromClient != null) fromClient.close();
            if(toClient != null) toClient.close();
            if(socket != null) socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}