package databases;

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
public class libraryDatabase {
    private static MongoClient mongo;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static final String URI = "mongodb+srv://jd52753:422cassignment7@library.b1aiqgy.mongodb.net/?retryWrites=true&w=majority";
    private static final String DB = "library"; // test
    private static final String COLLECTION = "products"; // test
    public static MongoCollection<Document> connectDatabase(){
        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB);
        collection = database.getCollection(COLLECTION);
//        ping();
//        findAndRead();
//        update();
        return collection;
    }

    public static void closeDatabase(){
        mongo.close();
    }
    public static void update() {
        Document doc = new Document();
        doc.put("itemType", "game");
        doc.put("name", "The Legendary Starfy");
        doc.put("creator", "Tose");
        doc.put("year", 2008);
        doc.put("summary", "the story of Starfy, a starfish-like creature who embarks on a quest to save his underwater kingdom from evil forces.");gi
        doc.put("currentCheckout", "");
        doc.put("previousCheckouts", "");

        collection.insertOne(doc);
    }
    public static void findAndRead() {
        MongoCursor cursor = collection.find(Filters.empty()).cursor();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
    public static void ping() {
        try {
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = database.runCommand(command);
            System.out.println("Connected successfully to server.");
        } catch (MongoException me) {
            System.err.println("An error occurred while attempting to run a command: " + me);
        }
    }
}
