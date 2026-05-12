package Project;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonArray extends JsonElement {

    private final List<JsonElement> list = new ArrayList<>();

    @Override
    protected void parseContent(String json) {
        json = json.trim();
        if (!json.startsWith("[") || !json.endsWith("]")) {
            throw new JsonException("Invalid JSON array");
        }

        String inner = json.substring(1, json.length() - 1).trim();
        if (inner.isEmpty()) {
            return;
        }

        int level = 0;
        boolean inString = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < inner.length(); i++) {
            char c = inner.charAt(i);

            if (c == '\\' && inString) {
                sb.append(c);
                if (i + 1 < inner.length()) {
                    sb.append(inner.charAt(++i));
                }
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
                    list.add(JsonElement.parse(sb.toString().trim()));
                    sb.setLength(0);
                    continue;
                }
            }
            sb.append(c);
        }

        if (sb.length() > 0 && !sb.toString().isBlank()) {
            list.add(JsonElement.parse(sb.toString().trim()));
        }
    }

    @Override
    public void validate() {
        for (JsonElement el : list) {
            if (el == null) {
                throw new JsonException("Null element in array");
            }
            el.validate();
        }
    }

    @Override
    public boolean search(String key) {
        boolean found = false;
        for (JsonElement el : list) {
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
        int index = parseIndex(parts[0]);

        if (index < 0 || index >= list.size()) {
            throw new JsonException("Index out of bounds: " + index);
        }

        if (parts.length == 1) {
            list.set(index, JsonElement.parse(value));
        } else {
            list.get(index).set(parts[1], value);
        }
    }

    @Override
    public void create(String path, String value) {
        if (path == null || path.isEmpty()) {
            list.add(JsonElement.parse(value));
            return;
        }
        String[] parts = path.split("/", 2);
        int index = parseIndex(parts[0]);

        if (index < 0 || index > list.size()) {
            throw new JsonException("Index out of bounds: " + index);
        }

        if (parts.length == 1) {
            if (index == list.size()) {
                list.add(JsonElement.parse(value));
            }
            else {
                throw new JsonException("Element already exists at index: " + index);
            }
        } else {
            if (index >= list.size()) {
                throw new JsonException("Path does not exist at index: " + index);
            }
            list.get(index).create(parts[1], value);
        }
    }

    @Override
    public void delete(String path) {
        if (path == null || path.isEmpty()) {
            throw new JsonException("Path required for delete");
        }
        String[] parts = path.split("/", 2);
        int index = parseIndex(parts[0]);

        if (index < 0 || index >= list.size()) {
            throw new JsonException("Index out of bounds: " + index);
        }

        if (parts.length == 1) {
            list.remove(index);
        }
        else {
            list.get(index).delete(parts[1]);
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
        try {
            int index = Integer.parseInt(parts[0]);
            if (index < 0 || index >= list.size()) {
                return null;
            }
            return parts.length > 1 ? list.get(index).findPath(parts[1]) : list.get(index);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private int parseIndex(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new JsonException("Invalid array index: " + s);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString());
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        return sb.append("]").toString();
    }
}