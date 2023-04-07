import data.Item;
import com.google.gson.Gson;

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
    private String clientUser;

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

    protected void sendToClient(Item item) {
        System.out.println("Sending to client: " + item);
        try {
//            toClient.writeUTF(new Gson().toJson(item));
//            toClient.flush();
            System.out.println("Item has been sent to client");
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Failed to send to client");
        }
    }

    @Override
    public void run() {
        Item input = null;
        while(socket.isConnected()){
            try{
                //need to fix read from server if nothing to read
                input = (Item) fromClient.readObject();
                System.out.println(input);
            } catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
                closeEverything(this.socket, this.fromClient, this.toClient);
                break;
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        this.sendToClient((Item) arg);
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