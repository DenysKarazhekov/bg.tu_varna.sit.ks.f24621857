package Project;

public abstract class JsonElement {

    public static JsonElement parse(String json) {
        if (json == null || json.isBlank()) {
            throw new IllegalArgumentException("JSON string is empty");
        }

        json = json.trim();

        if (json.startsWith("{")) {
            if (!json.endsWith("}")) {
                throw new IllegalArgumentException("Invalid JSON object");
            }
            JsonObject obj = new JsonObject();
            obj.parseContent(json);
            return obj;

        } else if (json.startsWith("[")) {
            if (!json.endsWith("]")) {
                throw new IllegalArgumentException("Invalid JSON array");
            }
            JsonArray array = new JsonArray();
            array.parseContent(json);
            return array;

        } else if (json.startsWith("\"") ||
                Character.isDigit(json.charAt(0)) ||
                json.equals("true") ||
                json.equals("false") ||
                json.equals("null")) {

            return new JsonPrimitive(json);

        } else {
            throw new IllegalArgumentException("Invalid JSON format");
        }
    }

    protected abstract void parseContent(String json);

    public abstract void save(String filePath) throws java.io.IOException;
    public abstract void save(String filePath, String jsonPath) throws Exception;
    public abstract void print();
    protected abstract JsonElement findPath(String path);
    public abstract void validate();
    public abstract void search(String key);
    public abstract void set(String path, String value);
    public abstract void create(String path, String value);
    public abstract void delete(String path);
    public abstract void move(String from, String to);
    public abstract void saveAs(String file, String path);
}