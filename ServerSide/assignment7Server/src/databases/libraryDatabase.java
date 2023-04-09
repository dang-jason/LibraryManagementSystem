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
    private static final String COLLECTION = "books"; // test
    public static MongoCollection<Document> connectDatabase(){
        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB);
        collection = database.getCollection(COLLECTION);
//        ping();
//        findAndRead();
        update();
        return collection;
    }

    public static void closeDatabase(){
        mongo.close();
    }
    public static void update() {
        Document doc = new Document();
        doc.put("name", "some book");
        doc.put("type", "book");
        doc.put("price", 0.0);
        doc.put("author", "someone");
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
