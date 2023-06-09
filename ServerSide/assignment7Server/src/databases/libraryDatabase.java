/*
 * EE422C Final Project submission by
 * <Jason Dang>
 * <jd52753>
 * <17185>
 * Spring 2023
 */
package databases;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import data.Item;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import org.bson.types.ObjectId;

public class libraryDatabase {
    private static MongoClient mongo;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static final String URI = "MONGODBSERVER"; //REPLACE THIS WITH DATABASE
    private static final String DB = "library"; // test
    private static final String COLLECTION = "products"; // test
    public static void connectDatabase(){
        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB);
        collection = database.getCollection(COLLECTION);
//        parseDB();
//        update();
    }

    public static MongoCollection<Document> getCollection() {
        return collection;
    }

    public static void closeDatabase(){
        mongo.close();
    }
    public static void update() {
        Document doc = new Document();
        doc.put("itemType", "game");
        doc.put("name", "Brain Age: Train Your Brain in Minutes a Day!");
        doc.put("creator", " Nintendo SPD");
        doc.put("year", 2005);
        doc.put("summary", "The game is designed to stimulate the brain and improve cognitive abilities through a series of daily exercises, such as solving math problems, memorizing words, and completing puzzles.");
        doc.put("currentCheckout", "");
        doc.put("previousCheckouts", "");
        collection.insertOne(doc);
    }
    public static ArrayList<Item> parseDB() {
        ArrayList<Item> items = new ArrayList<Item>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String pages_year;
                if(doc.containsKey("pages")) pages_year = "pages"; else pages_year = "year";
                Item i = new Item((String)doc.get("itemType"),(String)doc.get("name"), (String)doc.get("creator"), (Integer)doc.get(pages_year), (String)doc.get("summary"), (String)doc.get("currentCheckout"), (String)doc.get("previousCheckouts"),(String)doc.get("returnDate"), (String)doc.get("checkoutDate"), (String)doc.get("previousDates"), (String) doc.get("holders"));
                items.add(i);
            }
            return items;
        }
    }
}
