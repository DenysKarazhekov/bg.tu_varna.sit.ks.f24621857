package Project;

import java.io.FileWriter;
import java.io.IOException;

public class JsonPrimitive extends JsonElement {

    private String value;

    public JsonPrimitive(String value) {
        this.value = clean(value);
    }

    private String clean(String v) {
        if (v == null) return null;
        v = v.trim();
        if (v.startsWith("\"") && v.endsWith("\"") && v.length() >= 2) {
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
    public void validate() {
        if (value == null) {
            throw new JsonException("Null value found");
        }
        if (value.isBlank()) {
            throw new JsonException("Empty value found");
        }
    }

    @Override
    public boolean search(String key) {
        return false;
    }

    @Override
    public void set(String path, String newValue) {
        if (path == null || path.isEmpty()) {
            this.value = clean(newValue);
            return;
        }
        throw new JsonException("Cannot set inside primitive");
    }

    @Override
    public void create(String path, String value) {
        throw new JsonException("Cannot create inside primitive");
    }

    @Override
    public void delete(String path) {
        if (path == null || path.isEmpty()) {
            this.value = null;
            return;
        }
        throw new JsonException("Cannot delete inside primitive");
    }

    @Override
    public void move(String from, String to) {
        throw new JsonException("Cannot move inside primitive");
    }

    @Override
    public void save(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(this.toString());
        } catch (IOException e) {
            throw new JsonException("Error saving file");
        }
    }

    @Override
    public void save(String filePath, String jsonSubPath) {
        if (jsonSubPath == null || jsonSubPath.isEmpty()) {
            save(filePath);
            return;
        }
        throw new JsonException("Path not found in primitive");
    }

    @Override
    public void saveAs(String file, String path) {
        save(file, path);
    }

    @Override
    protected JsonElement findPath(String path) {
        return (path == null || path.isEmpty()) ? this : null;
    }

    @Override
    public String toString() {
        if (value == null || value.equals("null")) {
            return "null";
        }
        if (value.equals("true") || value.equals("false") || value.matches("-?\\d+(\\.\\d+)?")) {
            return value;
        }
        return "\"" + value + "\"";
    }
}