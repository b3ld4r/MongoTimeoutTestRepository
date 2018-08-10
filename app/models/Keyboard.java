package models;

import configuration.MongoConfig;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity(value = "keyboards")
public class Keyboard {

    @Id
    private Long id;

    private String brand;

    public Keyboard() {
    }

    public Keyboard(String brand) {
        id = getLastId() + 1;
        this.brand = brand;
        MongoConfig.datastore().save(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void update(String brand) {
        setBrand(brand);
        UpdateOperations<Keyboard> ops = MongoConfig.datastore().createUpdateOperations(Keyboard.class).set("brand", brand);
        MongoConfig.datastore().update(queryToFindSelf(), ops);
    }

    public void delete() {
        MongoConfig.datastore().delete(this);
    }

    public static Keyboard getById(Long id) {
        return MongoConfig.datastore().createQuery(Keyboard.class).field(Mapper.ID_KEY).equal(id).get();
    }

    public static List<Keyboard> getAll() {
        return MongoConfig.datastore().find(Keyboard.class).asList();
    }

    private Query<Keyboard> queryToFindSelf() {
        return MongoConfig.datastore().createQuery(Keyboard.class).field(Mapper.ID_KEY).equal(id);
    }

    private Long getLastId() {
        List<Keyboard> keyboards = getAll();
        if (keyboards == null || keyboards.isEmpty()) {
            return 0L;
        } else {
            List<Long> ids = new ArrayList<>();
            for (Keyboard keyboard : keyboards) {
                ids.add(keyboard.id);
            }

            return Collections.max(ids);
        }
    }
}
