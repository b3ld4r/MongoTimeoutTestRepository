package configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;
import play.Logger;

public class MongoConfig {

    private static Datastore datastore;

    public static Datastore datastore() {

        if (datastore == null) {
            initDatastore();
        }
        return datastore;
    }

    private static void initDatastore() {

        final Morphia morphia = new Morphia();

        morphia.mapPackage("app.models");
        MongoClient mongoClient;

        if (ConfigFactory.load().getString("mongodb.mode").compareTo("list") == 0) {

            Logger.info("MongoConfig.initDatastore: Mode is list");
            List<ServerAddress> serverAddressList = new ArrayList<>();

            Logger.info("MongoConfig.initDatastore: Reading hosts");
            List<? extends ConfigObject> list = ConfigFactory.load().getObjectList("mongodb.server_addresses");

            if (list != null && !list.isEmpty()) {
                for (ConfigObject object : list) {
                    Config tmp = object.toConfig();
                    serverAddressList.add(new ServerAddress(tmp.getString("host"), tmp.getInt("port")));
                    Logger.info("MongoConfig.initDatastore: Host: {}, port: {}", tmp.getString("host"), tmp.getInt("port"));
                }
            }

            Logger.info("MongoConfig.initDatastore: Setting options");
            MongoClientOptions.Builder clientOptions = new MongoClientOptions.Builder();
            clientOptions.connectionsPerHost(ConfigFactory.load().getInt("mongodb.max_pool_size"));
            Logger.info("MongoConfig.initDatastore: Max pool size is: {}", ConfigFactory.load().getInt("mongodb.max_pool_size"));

            Logger.info("MongoConfig.initDatastore: Creating client");
            mongoClient = new MongoClient(serverAddressList, clientOptions.build());
            Logger.info("MongoConfig.initDatastore: Client created");
        } else if (ConfigFactory.load().getString("mongodb.mode").compareTo("uri") == 0) {

            Logger.info("MongoConfig.initDatastore: Mode is uri");
            Logger.info("MongoConfig.initDatastore: Creating client");
            mongoClient = new MongoClient(new MongoClientURI(ConfigFactory.load().getString("mongodb.uri")));
            Logger.info("MongoConfig.initDatastore: Client created");
        } else {
            Logger.info("MongoConfig.initDatastore: No mode supplied in mongo configuration");
            throw new RuntimeException("No mode supplied in mongo configuration");
        }

        Logger.info("MongoConfig.initDatastore: Creating datastore");
        datastore = morphia.createDatastore(
                mongoClient, ConfigFactory.load().getString("mongodb.db_name"));
        Logger.info("MongoConfig.initDatastore: Datastore created");
    }
}