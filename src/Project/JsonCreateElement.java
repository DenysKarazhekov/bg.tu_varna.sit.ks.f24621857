package Project;

public class JsonCreateElement extends BaseCase {

    public JsonCreateElement(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        if (cp.requireFile() && cp.requireArgs(parts, 3, "Usage: create <path> <value>")) {

            String path = parts[1];
            String value = cp.extractPath(input, parts, 2);

            try {
                cp.getJsonElement().create(path, value);
                System.out.println("Successfully created element at [" + path + "]");
            } catch (Exception e) {
               System.out.println("Error: Could not create element. " + e.getMessage());
            }
        }
        return true;
    }
}