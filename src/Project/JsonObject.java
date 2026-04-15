package Project;

public class JsonObject extends JsonElement {

    public JsonObject() {
    }

    @Override
    protected void parseContent(String json) {
        System.out.println("parse object: " + json);
    }

    @Override
    public void validate() {
        System.out.println("validate object");
    }

    @Override
    public void print() {
        System.out.println("print object");
    }

    @Override
    protected JsonElement findPath(String path) {
        return null;
    }

    @Override
    public void search(String key) {
        System.out.println("search in object: " + key);
    }

    @Override
    public void set(String path, String value) {
        System.out.println("set in object");
    }

    @Override
    public void create(String path, String value) {
        System.out.println("create in object");
    }

    @Override
    public void delete(String path) {
        System.out.println("delete in object");
    }

    @Override
    public void move(String from, String to) {
        System.out.println("move in object");
    }

    @Override
    public void save(String filePath) {
        System.out.println("save object");
    }

    @Override
    public void save(String filePath, String jsonPath) {
        System.out.println("save subtree: " + jsonPath);
    }

    @Override
    public void saveAs(String file, String path) {
        System.out.println("saveAs object");
    }
}