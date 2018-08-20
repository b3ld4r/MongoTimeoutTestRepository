package configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.typesafe.config.ConfigFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;

public class MongoConfig {

    private static Datastore datastore;

    public static Datastore datastore() {

        if (datastore == null) {
            initDatastore();
        }
        return datastore;
    }

    public static void initDatastore() {

        final Morphia morphia = new Morphia();

        morphia.mapPackage("app.models");

        MongoClient mongoClient = new MongoClient(new MongoClientURI(ConfigFactory.load().getString("mongodb.uri")));

        datastore = morphia.createDatastore(
                mongoClient, ConfigFactory.load().getString("mongodb.db_name"));
    }
}
