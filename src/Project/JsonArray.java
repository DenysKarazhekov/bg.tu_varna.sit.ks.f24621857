package Project;

public class JsonArray extends JsonElement {

    public JsonArray() {
        System.out.println("JsonArray created");
    }

    public void validate() {
        System.out.println("validate array");
    }

    public void print() {
        System.out.println("print array");
    }

    public void search(String key) {
        System.out.println("search in array: " + key);
    }

    public void set(String path, String value) {
        System.out.println("set in array");
    }

    public void create(String path, String value) {
        System.out.println("create in array");
    }

    public void delete(String path) {
        System.out.println("delete in array");
    }

    public void move(String from, String to) {
        System.out.println("move in array");
    }

    public void save(String path) {
        System.out.println("save array");
    }

    public void saveAs(String file, String path) {
        System.out.println("saveAs array");
    }
}