package Project;

public class JsonObject extends JsonElement {

    public JsonObject() {
        System.out.println("JsonObject created");
    }

    public void validate() {
        System.out.println("validate object");
    }

    public void print() {
        System.out.println("print object");
    }

    public void search(String key) {
        System.out.println("search in object: " + key);
    }

    public void set(String path, String value) {
        System.out.println("set in object");
    }

    public void create(String path, String value) {
        System.out.println("create in object");
    }

    public void delete(String path) {
        System.out.println("delete in object");
    }

    public void move(String from, String to) {
        System.out.println("move in object");
    }

    public void save(String path) {
        System.out.println("save object");
    }

    public void saveAs(String file, String path) {
        System.out.println("saveAs object");
    }
}