package Project;

public abstract class JsonElement {

    public static JsonElement parse(String json) {

        if (json == null || json.isBlank()) {
            throw new JsonException("Empty JSON");
        }

        json = json.trim();

        char first = json.charAt(0);

        if (first == '{') {
            JsonObject obj = new JsonObject();
            obj.parseContent(json);
            return obj;
        }

        if (first == '[') {
            JsonArray arr = new JsonArray();
            arr.parseContent(json);
            return arr;
        }

        return new JsonPrimitive(json);
    }

    protected abstract void parseContent(String json);

    public abstract void print();

    public abstract void validate();

    public abstract boolean search(String key);

    public abstract void set(String path, String value);

    public abstract void create(String path, String value);

    public abstract void delete(String path);

    public abstract void move(String from, String to);

    public abstract void save(String filePath, String jsonSubPath);

    public abstract void save(String filePath) throws java.io.IOException;

    public abstract void saveAs(String file, String path);

    protected abstract JsonElement findPath(String path);
}