package databases;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

public class userDatabase {
    private static MongoClient mongo;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static final String URI = "mongodb+srv://jd52753:422cassignment7@library.b1aiqgy.mongodb.net/?retryWrites=true&w=majority";
    private static final String DB = "library";
    private static final String COLLECTION = "profiles";

    public static void connectDatabase(){
        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB);
        collection = database.getCollection(COLLECTION);
    }
    public static MongoCollection<Document> getCollection(){
        return collection;
    }
    public static void closeDatabase(){
        mongo.close();
    }
}
