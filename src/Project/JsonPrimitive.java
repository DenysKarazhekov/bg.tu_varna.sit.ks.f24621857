package Project;

import java.io.FileWriter;
import java.io.IOException;

public class JsonPrimitive extends JsonElement {

    private String value;

    public JsonPrimitive(String value) {
        if (value == null || value.isBlank()) {
            throw new JsonException("Primitive value cannot be empty");
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
        if (jsonPath != null && !jsonPath.isEmpty()) {
            throw new JsonException("Primitive has no sub-paths");
        }

        try {
            save(filePath);
        } catch (IOException e) {
            throw new JsonException("Error saving primitive");
        }
    }

    @Override
    public void print() {
        System.out.println(value);
    }

    @Override
    protected JsonElement findPath(String path) {
        return (path == null || path.isEmpty()) ? this : null;
    }

    @Override
    public void validate() {
        if (value == null || value.isBlank()) {
            throw new JsonException("Primitive is empty");
        }
    }

    @Override
    public void search(String key) {
    }

    @Override
    public void set(String path, String value) {
        if (path != null && !path.isEmpty()) {
            throw new JsonException("Invalid path for primitive");
        }

        if (value == null || value.isBlank()) {
            throw new JsonException("Value cannot be empty");
        }

        this.value = value.trim();
    }

    @Override
    public void create(String path, String value) {
        throw new JsonException("Cannot create inside primitive");
    }

    @Override
    public void delete(String path) {
        if (path != null && !path.isEmpty()) {
            throw new JsonException("Invalid path for delete");
        }

        this.value = null;
    }

    @Override
    public void move(String from, String to) {
        throw new JsonException("Move not supported for primitive");
    }

    @Override
    public void saveAs(String file, String path) {
        if (path != null && !path.isEmpty()) {
            throw new JsonException("Primitive has no sub-paths");
        }

        try {
            save(file);
        } catch (IOException e) {
            throw new JsonException("Error saving file");
        }
    }
}