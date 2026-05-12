package Project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonObject extends JsonElement {

    private final Map<String, JsonElement> map = new LinkedHashMap<>();

    @Override
    protected void parseContent(String json) {
        if (json == null || json.isBlank()) {
            throw new JsonException("Object is empty");
        }
        json = json.trim();
        if (!json.startsWith("{") || !json.endsWith("}")) {
            throw new JsonException("Invalid JSON object");
        }

        String inner = json.substring(1, json.length() - 1).trim();
        if (inner.isEmpty()) {
            return;
        }

        int start = 0;
        boolean inString = false;
        int level = 0;

        for (int i = 0; i < inner.length(); i++) {
            char c = inner.charAt(i);

            if (c == '\\' && inString) {
                i++;
                continue;
            }

            if (c == '"') {
                inString = !inString;
            }

            if (!inString) {
                if (c == '{' || c == '[') {
                    level++;
                }
                else if (c == '}' || c == ']') {
                    level--;
                }
                else if (c == ',' && level == 0) {
                    parsePair(inner.substring(start, i));
                    start = i + 1;
                }
            }
        }
        parsePair(inner.substring(start));
    }

    private void parsePair(String pair) {
        if (pair == null || pair.trim().isEmpty()) {
            return;
        }
        pair = pair.trim();

        int colonIndex = -1;
        boolean inString = false;

        for (int i = 0; i < pair.length(); i++) {
            char c = pair.charAt(i);
            if (c == '\\' && inString) {
                i++;
                continue;
            }
            if (c == '"') {
                inString = !inString;
            }
            if (c == ':' && !inString) {
                colonIndex = i;
                break;
            }
        }

        if (colonIndex == -1) {
            throw new JsonException("Missing ':' in object");
        }

        String key = pair.substring(0, colonIndex).trim();
        String value = pair.substring(colonIndex + 1).trim();

        if (key.startsWith("\"") && key.endsWith("\"") && key.length() >= 2) {
            key = key.substring(1, key.length() - 1);
        } else {
            throw new JsonException("Invalid key format: " + key);
        }

        map.put(key, JsonElement.parse(value));
    }


    @Override
    public void validate() {
        for (var entry : map.entrySet()) {
            if (entry.getKey() == null || entry.getKey().isEmpty()) {
                throw new JsonException("Invalid key");
            }
            if (entry.getValue() == null) {
                throw new JsonException("Null value for key: " + entry.getKey());
            }
            entry.getValue().validate();
        }
    }

    @Override
    public boolean search(String key) {
        boolean found = false;
        if (map.containsKey(key)) {
            System.out.println(map.get(key).toString());
            found = true;
        }
        for (JsonElement el : map.values()) {
            if (el.search(key)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public void set(String path, String value) {
        if (path == null || path.isEmpty()) {
            throw new JsonException("Path required");
        }
        String[] parts = path.split("/", 2);

        if (!map.containsKey(parts[0])) {
            throw new JsonException("Key not found: " + parts[0]);
        }

        if (parts.length == 1) {
            map.put(parts[0], JsonElement.parse(value));
        } else {
            map.get(parts[0]).set(parts[1], value);
        }
    }

    @Override
    public void create(String path, String value) {
        if (path == null || path.isEmpty()) {
            throw new JsonException("Path required");
        }
        String[] parts = path.split("/", 2);

        if (parts.length == 1) {
            if (map.containsKey(parts[0])) {
                throw new JsonException("Key already exists: " + parts[0]);
            }
            map.put(parts[0], JsonElement.parse(value));
        } else {
            if (!map.containsKey(parts[0])) {
                map.put(parts[0], new JsonObject());
            }
            map.get(parts[0]).create(parts[1], value);
        }
    }

    @Override
    public void delete(String path) {
        if (path == null || path.isEmpty()) {
            throw new JsonException("Path required for delete");
        }
        String[] parts = path.split("/", 2);

        if (!map.containsKey(parts[0])) {
            throw new JsonException("Key not found: " + parts[0]);
        }

        if (parts.length == 1) {
            map.remove(parts[0]);
        }
        else {
            map.get(parts[0]).delete(parts[1]);
        }
    }

    @Override
    public void move(String from, String to) {
        JsonElement el = findPath(from);
        if (el == null) {
            throw new JsonException("Source path not found: " + from);
        }
        String value = el.toString();
        delete(from);
        create(to, value);
    }

    @Override
    public void save(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(this.toString());
        }
    }

    @Override
    public void save(String filePath, String jsonSubPath) throws IOException {
        JsonElement el = findPath(jsonSubPath);
        if (el == null) {
            throw new JsonException("Path not found: " + jsonSubPath);
        }
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(el.toString());
        }
    }

    @Override
    public void saveAs(String file, String path) throws IOException {
        save(file, path);
    }

    @Override
    protected JsonElement findPath(String path) {
        if (path == null || path.isEmpty()) {
            return this;
        }
        String[] parts = path.split("/", 2);
        if (!map.containsKey(parts[0])) {
            return null;
        }
        JsonElement el = map.get(parts[0]);
        return parts.length > 1 ? el.findPath(parts[1]) : el;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (var entry : map.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            sb.append("\"").append(entry.getKey()).append("\":").append(entry.getValue().toString());
        }
        return sb.append("}").toString();
    }
}