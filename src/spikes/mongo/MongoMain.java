package spikes.mongo;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

public class MongoMain {

    public static void main(String[] args) {
        try {
            Mongo m = new Mongo("localhost", 27017);
            DB mongoTestDBConnection = m.getDB("mongotest");

            //Find existing collections
            System.out.println("collections are:");
            Set<String> collectionNames = mongoTestDBConnection.getCollectionNames();
            for (String collectionName : collectionNames) {
                System.out.println(collectionName);
            }

            //Find count for particular collection
            DBCollection mongoTestCollection = mongoTestDBConnection.getCollection("home");
            DBCursor dbObjects = mongoTestCollection.find();
            System.out.println("mongo test collection count: " + dbObjects.count());

            //Insert a document in collection
            BasicDBObject person = new BasicDBObject();
            person.put("name", "John");
            person.put("age", "42");

            BasicDBObject address = new BasicDBObject();
            address.put("city", "NYC");
            address.put("country", "USA");

            person.put("address", address);

            DBCollection personCollection = mongoTestDBConnection.createCollection("Person", null);
            personCollection.insert(person);
            DBCursor personCollectionCurson = personCollection.find();
            System.out.println("personCollection collection count: " + personCollectionCurson.count());

            //Find particular document
            BasicDBObject searchQuery = new BasicDBObject("name", "John");
            DBCursor personCursor = personCollection.find(searchQuery);
            while(personCursor.hasNext() ){
                System.out.println(personCursor.next());
            }


            //Creating index on particular field
            personCollection.ensureIndex("name");
            List<DBObject> list = personCollection.getIndexInfo();

            for (DBObject o : list) {
                System.out.println(o);
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
