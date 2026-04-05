package Project;

public class JsonDeleteElement extends BaseCase {

    public JsonDeleteElement(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
       if (cp.requireFile() && cp.requireArgs(parts, 2, "Usage: delete <path>")) {

            String path = parts[1];

            try {
                cp.getJsonElement().delete(path);
                System.out.println("Successfully deleted element at [" + path + "]");
            } catch (Exception e) {
                System.out.println("Error: Could not delete element. " + e.getMessage());
            }
        }
        return true;
    }
}