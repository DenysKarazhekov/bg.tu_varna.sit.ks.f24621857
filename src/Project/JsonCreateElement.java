package Project;

public class JsonCreateElement extends BaseCase {

    public JsonCreateElement(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireFile()) {
            return true;
        }

        if (cp.requireArgs(parts, 3, "Usage: create <path> <value>")) {
            return true;
        }

        String path = parts[1];
        String value = cp.extractPath(input, parts, 2);

        if (path.isBlank()) {
            System.out.println("Error: path cannot be empty");
            return true;
        }

        if (value == null || value.isBlank()) {
            System.out.println("Error: value cannot be empty");
            return true;
        }

        try {
            cp.getJsonElement().create(path, value);
            System.out.println("Successfully created " + path);
        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}