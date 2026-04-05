package Project;

public class JsonSetElement extends BaseCase {

    public JsonSetElement(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        if (cp.requireFile() && cp.requireArgs(parts, 3, "Usage: set <path> <string>")) {

            String path = parts[1];
            String newValue = cp.extractPath(input, parts, 2);

            try {
                cp.getJsonElement().set(path, newValue);
                System.out.println("Successfully updated element at [" + path + "]");
            } catch (Exception e) {
               System.out.println("Error: Could not set value. " + e.getMessage());
            }
        }
        return true;
    }
}