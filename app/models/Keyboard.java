package models;

public class Keyboard {

    public static final int DEFAULT_ID = 0;
    public static final String DEFAULT_BRAND = "dell";

    private int id;

    private String brand;

    public Keyboard() {
        this(DEFAULT_ID, DEFAULT_BRAND);
    }

    public Keyboard(int id, String brand) {
        this.id = id;
        this.brand = brand;
    }

    public Keyboard(String brand) {
        this(DEFAULT_ID, brand);
    }

    public Keyboard(int id) {
        this(id, DEFAULT_BRAND);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
