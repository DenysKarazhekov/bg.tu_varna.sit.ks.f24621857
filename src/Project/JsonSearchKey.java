package Project;

public class JsonSearchKey extends BaseCase {

    public JsonSearchKey(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireArgs(parts, 2, "Usage: search <key>")) {
            return true;
        }

        String key = parts[1];

        try {
            boolean found = cp.getJsonElement().search(key);
            if (!found) {
                System.out.println("No matches found");
            }
        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}