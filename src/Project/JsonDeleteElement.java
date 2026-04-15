package Project;

public class JsonDeleteElement extends BaseCase {

    public JsonDeleteElement(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireFile()) {
            return true;
        }

        if (!cp.requireArgs(parts, 2, "Usage: delete <path>")) {
            return true;
        }

        String path = parts[1];

        try {
            cp.getJsonElement().delete(path);
            System.out.println("Deleted: " + path);
        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}