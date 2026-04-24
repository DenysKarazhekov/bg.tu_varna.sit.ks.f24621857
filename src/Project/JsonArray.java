package Project;

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
        if (inner.isEmpty()) return;

        int level = 0;
        boolean inString = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < inner.length(); i++) {

            char c = inner.charAt(i);

            if (c == '"' && (i == 0 || inner.charAt(i - 1) != '\\')) {
                inString = !inString;
            }

            if (!inString) {
                if (c == '{' || c == '[') level++;
                if (c == '}' || c == ']') level--;

                if (c == ',' && level == 0) {
                    list.add(JsonElement.parse(sb.toString().trim()));
                    sb.setLength(0);
                    continue;
                }
            }

            sb.append(c);
        }

        if (!sb.toString().isBlank()) {
            list.add(JsonElement.parse(sb.toString().trim()));
        }
    }

    private void addElement(String part) {

        if (part == null) {
            return;
        }

        String value = part.trim();

        if (!value.isEmpty()) {
            list.add(JsonElement.parse(value));
        }
    }

    @Override
    public void print() {

        System.out.print("[");

        for (int i = 0; i < list.size(); i++) {

            list.get(i).print();

            if (i < list.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }

    @Override
    public void validate() {
        System.out.println("validate array");
    }

    @Override
    public boolean search(String key) {
        System.out.println("search in array: " + key);
        return false;
    }

    @Override
    public void set(String path, String value) {
        System.out.println("set in array");
    }

    @Override
    public void create(String path, String value) {
        System.out.println("create in array");
    }

    @Override
    public void delete(String path) {
        System.out.println("delete in array");
    }

    @Override
    public void move(String from, String to) {
        System.out.println("move in array");
    }

    @Override
    public void save(String filePath) {
        System.out.println("save array");
    }

    @Override
    public void save(String filePath, String jsonSubPath) {
        System.out.println("save array");
    }

    @Override
    public void saveAs(String file, String path) {
        System.out.println("saveAs array");
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

            JsonElement el = list.get(index);

            if (parts.length > 1) {
                return el.findPath(parts[1]);
            }

            return el;

        } catch (NumberFormatException e) {
            return null;
        }
    }
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < list.size(); i++) {

            sb.append(list.get(i).toString());

            if (i < list.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");

        return sb.toString();
    }
}