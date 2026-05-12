package Project;

public class JsonCreateElement extends BaseCase {

    public JsonCreateElement(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireArgs(parts, 3, "Usage: create <path> <value>")) {
            return true;
        }

        String path = parts[1];
        String value = cp.extractPath(input, parts, 2);

        try {
            cp.getJsonElement().create(path, value);
            System.out.println("Successfully created " + path);
        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}