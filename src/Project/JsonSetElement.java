package Project;

public class JsonSetElement extends BaseCase {

    public JsonSetElement(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireFile()) {
            return true;
        }

        if (cp.requireArgs(parts, 3, "Usage: set <path> <value>")) {
            return true;
        }

        String path = parts[1];
        String newValue = cp.extractPath(input, parts, 2);

        if (path.isBlank()) {
            System.out.println("Error: path cannot be empty");
            return true;
        }

        if (newValue == null || newValue.isBlank()) {
            System.out.println("Error: value cannot be empty");
            return true;
        }

        try {
            cp.getJsonElement().set(path, newValue);
            System.out.println("Successfully updated " + path);
        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}