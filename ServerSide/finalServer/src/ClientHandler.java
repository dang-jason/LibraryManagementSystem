/*
 * EE422C Final Project submission by
 * <Jason Dang>
 * <jd52753>
 * <17185>
 * Spring 2023
 */
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
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
import java.util.Arrays;
import java.util.Observer;
import java.util.Observable;

import static com.mongodb.client.model.Updates.combine;

class ClientHandler implements Runnable, Observer {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Server server;
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
                toClient.reset();
                toClient.writeUnshared(query);
                toClient.flush();
                toClient.reset();
                toClient.writeUnshared(item);
                toClient.flush();
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
        while(!socket.isClosed()){
            try{
                while((query = (String) fromClient.readObject()) != null) {
                    if(query.equals("exit")){
                        closeEverything(this.socket, this.fromClient, this.toClient);
                        server.deleteObserver(this);
                        clientHandlers.remove(this);
                        break;
                    }
                    item = (Item) fromClient.readObject();
                    if(query.equals("checkout")){
                        server.processRequest(item);
                        Bson filter = Filters.eq("name", item.getName());
                        Bson update = combine(
                                Updates.set("currentCheckout", item.getCurrent()),
                                Updates.set("returnDate", item.getReturnDate()),
                                Updates.set("checkoutDate", item.getCheckoutDate())
                        );
                        libraryDatabase.getCollection().updateOne(filter, update);
                    }else if(query.equals("return")){
                        server.processRequest(item);
                        Bson filter = Filters.eq("name", item.getName());
                        Bson update = combine(
                                Updates.set("currentCheckout", item.getCurrent()),
                                Updates.set("previousCheckouts", item.getPrevious()),
                                Updates.set("returnDate", item.getReturnDate()),
                                Updates.set("checkoutDate", item.getCheckoutDate()),
                                Updates.set("previousDates", item.getPreviousDates())
                        );
                        Document doc = libraryDatabase.getCollection().find(Filters.eq("name", item.getName())).first();
                        libraryDatabase.getCollection().updateOne(filter, update);
                    }else if(query.equals("hold")){
                        server.processRequest(item);
                        libraryDatabase.getCollection().updateOne(Filters.eq("name", item.getName()), Updates.set("holders", item.getHolders()));
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