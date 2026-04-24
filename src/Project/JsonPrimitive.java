package Project;

public class JsonPrimitive extends JsonElement {

    private String value;

    public JsonPrimitive(String value) {
        this.value = clean(value);
    }

    private String clean(String v) {

        if (v == null) {
            return null;
        }

        v = v.trim();

        if (v.startsWith("\"") && v.endsWith("\"")) {
            return v.substring(1, v.length() - 1);
        }

        return v;
    }

    @Override
    protected void parseContent(String json) {

        if (json == null || json.isBlank()) {
            throw new JsonException("Primitive is empty");
        }

        this.value = clean(json);
    }

    @Override
    public void print() {

        if (value == null) {
            System.out.print("null");
            return;
        }

        System.out.print(value);
    }

    @Override
    public void validate() {
        System.out.println("validate primitive");
    }

    @Override
    public boolean search(String key) {
        System.out.println("search in primitive");
        return false;
    }

    @Override
    public void set(String path, String value) {
        System.out.println("set in primitive");
    }

    @Override
    public void create(String path, String value) {
        System.out.println("create in primitive");
    }

    @Override
    public void delete(String path) {
        System.out.println("delete in primitive");
    }

    @Override
    public void move(String from, String to) {
        System.out.println("move in primitive");
    }

    @Override
    public void save(String filePath) {
        System.out.println("save primitive");
    }

    @Override
    public void save(String filePath, String jsonSubPath) {
        System.out.println("save primitive");
    }

    @Override
    public void saveAs(String file, String path) {
        System.out.println("saveAs primitive");
    }

    @Override
    protected JsonElement findPath(String path) {

        if (path == null || path.isEmpty()) {
            return this;
        }

        return null;
    }
    @Override
    public String toString() {

        if (value == null) return "null";

        if (value.equals("true") || value.equals("false") || value.matches("-?\\d+")) {
            return value;
        }

        return "\"" + value + "\"";
    }
}