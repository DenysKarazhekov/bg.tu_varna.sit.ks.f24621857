package Project;

import java.util.ArrayList;
import java.util.List;

public class JsonArray extends JsonElement {

    private final List<JsonElement> elements = new ArrayList<>();

    public JsonArray() {
    }
    @Override
    protected void parseContent(String json) {

        json = json.trim();

        if (!json.startsWith("[") || !json.endsWith("]")) {
            throw new JsonException("Invalid array format");
        }

        String inner = json.substring(1, json.length() - 1).trim();

        if (inner.isEmpty()) {
            return;
        }

        String[] parts = inner.split(",");

        for (String part : parts) {
            JsonElement element = JsonElement.parse(part.trim());
            elements.add(element);
        }
    }

    @Override
    public void validate() {

        for (JsonElement el : elements) {
            if (el == null) {
                throw new JsonException("Array contains null element");
            }
            el.validate();
        }
    }

    @Override
    public void print() {

        System.out.print("[");

        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).print();

            if (i < elements.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }

    @Override
    protected JsonElement findPath(String path) {
        return null;
    }

    @Override
    public void search(String key) {
        System.out.println("search in array: " + key);
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
    public void save(String filePath, String jsonPath) {
        System.out.println("save subtree array: " + jsonPath);
    }

    @Override
    public void saveAs(String file, String path) {
        System.out.println("saveAs array");
    }
}