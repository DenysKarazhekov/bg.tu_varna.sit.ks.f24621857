package Project;

import java.io.FileWriter;
import java.io.IOException;

public class JsonPrimitive extends JsonElement {

    private String value;

    public JsonPrimitive(String value) {
        if (value == null) {
            throw new JsonException("Primitive value cannot be null");
        }
        this.value = value.trim();
    }

    @Override
    protected void parseContent(String json) {
        if (json == null || json.isBlank()) {
            throw new JsonException("Invalid primitive value");
        }
        this.value = json.trim();
    }

    @Override
    public void save(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(value);
        }
    }

    @Override
    public void save(String filePath, String jsonPath) {
        try {
            save(filePath);
        } catch (IOException e) {
            throw new JsonException("Error saving primitive: " + e.getMessage());
        }
    }

    @Override
    public void print() {
        System.out.println(value);
    }

    @Override
    protected JsonElement findPath(String path) {
        if (path == null || path.isEmpty()) {
            return this;
        }
        return null;
    }

    @Override
    public void validate() {
        if (value == null) {
            throw new JsonException("Primitive is null");
        }
    }

    @Override
    public void search(String key) {
    }

    @Override
    public void set(String path, String value) {
        if (path == null || path.isEmpty()) {
            if (value == null) {
                throw new JsonException("New value cannot be null");
            }
            this.value = value;
        } else {
            throw new JsonException("Invalid path for primitive");
        }
    }

    @Override
    public void create(String path, String value) {
        throw new JsonException("Cannot create inside primitive");
    }

    @Override
    public void delete(String path) {
        if (path == null || path.isEmpty()) {
            this.value = null;
        } else {
            throw new JsonException("Invalid path for delete in primitive");
        }
    }

    @Override
    public void move(String from, String to) {
        throw new JsonException("Move operation not supported for primitive");
    }

    @Override
    public void saveAs(String file, String path) {
        if (path == null || path.isEmpty()) {
            try {
                save(file);
            } catch (IOException e) {
                throw new JsonException("Error saving file: " + e.getMessage());
            }
        } else {
            throw new JsonException("Invalid path for primitive saveAs");
        }
    }
}