/*
 * EE422C Final Project submission by
 * <Jason Dang>
 * <jd52753>
 * <17185>
 * Spring 2023
 */
package databases;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.ByteArrayOutputStream;

public class userDatabase {
    private static MongoClient mongo;
    private static MongoDatabase database;
    private static MongoCollection<Document> collection;
    private static GridFSBucket gridFSBucket;
    private static final String URI = "MONGODB DATABASE" //CHANGE TO MONGODB DATABASE
    private static final String DB = "library";
    private static final String COLLECTION = "profiles";

    public static void connectDatabase(){
        mongo = MongoClients.create(URI);
        database = mongo.getDatabase(DB);
        collection = database.getCollection(COLLECTION);
        gridFSBucket = GridFSBuckets.create(database, "Images");
    }
    public static MongoCollection<Document> getCollection(){
        return collection;
    }

    public static byte[] getImage(String name){
        GridFSFile gridFSFile = gridFSBucket.find(Filters.eq("filename", name)).first();
        if (gridFSFile != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            gridFSBucket.downloadToStream(gridFSFile.getObjectId(), outputStream);
            return outputStream.toByteArray();
        }
        return null;
    }
    public static void closeDatabase(){
        mongo.close();
    }
}
