package Project;

public class JsonSearchKey extends BaseCase {

    public JsonSearchKey(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireFile()) {
            return true;
        }

        if (!cp.requireArgs(parts, 2, "Usage: search <key>")) {
            return true;
        }

        String keyToSearch = parts[1];

        if (keyToSearch == null || keyToSearch.isBlank()) {
            System.out.println("Error: key cannot be empty");
            return true;
        }

        try {
            cp.getJsonElement().search(keyToSearch);
        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: search failed");
        }

        return true;
    }
}