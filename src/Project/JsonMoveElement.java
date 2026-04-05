package Project;

public class JsonMoveElement extends BaseCase {

    public JsonMoveElement(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        if (cp.requireFile() && cp.requireArgs(parts, 3, "Usage: move <from> <to>")) {

            String fromPath = parts[1];
            String toPath = parts[2];

            try {
                cp.getJsonElement().move(fromPath, toPath);
                System.out.println("Successfully moved elements from [" + fromPath + "] to [" + toPath + "]");
            } catch (Exception e) {
                System.out.println("Error during move: " + e.getMessage());
            }
        }
        return true;
    }
}