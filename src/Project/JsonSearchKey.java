package Project;

public class JsonSearchKey extends BaseCase {

    public JsonSearchKey(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        if (cp.requireFile() && cp.requireArgs(parts, 2, "Usage: search <key>")) {

            String keyToSearch = parts[1];

            try {
                System.out.println("Searching for all occurrences of key: [" + keyToSearch + "]...");
                cp.getJsonElement().search(keyToSearch);
            } catch (Exception e) {
                System.out.println("Error during search: " + e.getMessage());
            }
        }
        return true;
    }
}