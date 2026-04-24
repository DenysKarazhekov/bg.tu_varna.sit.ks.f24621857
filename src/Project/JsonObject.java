package Project;

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
        int braces = 0;
        int brackets = 0;
        boolean escaped = false;

        for (int i = 0; i < inner.length(); i++) {

            char c = inner.charAt(i);

            if (escaped) {
                escaped = false;
                continue;
            }

            if (c == '\\') {
                escaped = true;
                continue;
            }

            if (c == '"') {
                inString = !inString;
            }

            if (!inString) {
                if (c == '{') {
                    braces++;
                }
                else if (c == '}') {
                    braces--;
                }
                else if (c == '[') {
                    brackets++;
                }
                else if (c == ']') {
                    brackets--;
                }

                if (c == ',' && braces == 0 && brackets == 0) {
                    parsePair(inner.substring(start, i));
                    start = i + 1;
                }
            }
        }

        parsePair(inner.substring(start));
    }

    private void parsePair(String pair) {

        if (pair == null) {
            return;
        }

        pair = pair.trim();

        if (pair.isEmpty()) {
            return;
        }

        int colonIndex = pair.indexOf(':');

        if (colonIndex == -1) {
            throw new JsonException("Missing ':' in object");
        }

        String key = pair.substring(0, colonIndex).trim();
        String value = pair.substring(colonIndex + 1).trim();

        if (key.startsWith("\"") && key.endsWith("\"")) {
            key = key.substring(1, key.length() - 1);
        } else {
            throw new JsonException("Invalid key format");
        }

        map.put(key, JsonElement.parse(value));
    }

    @Override
    public void print() {

        System.out.println("{");

        int i = 0;

        for (Map.Entry<String, JsonElement> entry : map.entrySet()) {

            System.out.print("  \"" + entry.getKey() + "\": ");
            entry.getValue().print();

            if (i < map.size() - 1) {
                System.out.println(",");
            } else {
                System.out.println();
            }

            i++;
        }

        System.out.println("}");
    }

    @Override
    public void validate() {
        System.out.println("validate object");
    }

    @Override
    public boolean search(String key) {
        System.out.println("search in object: " + key);
        return false;
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
    public void save(String filePath, String jsonSubPath) {
        System.out.println("save object");
    }
    @Override
    public void save(String filePath) {
        System.out.println("save object");
    }
    @Override
    public void saveAs(String file, String path) {
        System.out.println("saveAs object");
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

        if (parts.length > 1) {
            return el.findPath(parts[1]);
        }

        return el;
    }
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("{");

        boolean first = true;

        for (var entry : map.entrySet()) {

            if (!first) {
                sb.append(", ");
            }

            first = false;

            sb.append("\"").append(entry.getKey()).append("\": ");
            sb.append(entry.getValue().toString());
        }

        sb.append("}");

        return sb.toString();
    }
}