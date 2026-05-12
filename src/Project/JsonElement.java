package Project;

import java.io.IOException;

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

    public void print() {
        System.out.println(detailedPrint(toString()));
    }

    private String detailedPrint(String json) {
        StringBuilder sb = new StringBuilder();
        int indent = 0;
        boolean inString = false;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);

            if (c == '"') inString = !inString;

            if (inString) {
                sb.append(c);
                continue;
            }

            if (c == '{' || c == '[') {
                sb.append(c).append('\n');
                indent++;
                sb.append("  ".repeat(indent));
            } else if (c == '}' || c == ']') {
                sb.append('\n');
                indent--;
                sb.append("  ".repeat(indent)).append(c);
            } else if (c == ',') {
                sb.append(c).append('\n');
                sb.append("  ".repeat(indent));
            } else if (c == ':') {
                sb.append(": ");
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
    public JsonElement getByPath(String path) {
        return findPath(path);
    }
    public abstract void validate();

    public abstract boolean search(String key);

    public abstract void set(String path, String value);

    public abstract void create(String path, String value);

    public abstract void delete(String path);

    public abstract void move(String from, String to);

    public abstract void save(String filePath, String jsonSubPath) throws IOException;

    public abstract void save(String filePath) throws IOException;

    public abstract void saveAs(String file, String path) throws IOException ;


    protected abstract JsonElement findPath(String path);
}