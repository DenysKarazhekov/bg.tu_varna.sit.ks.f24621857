package Project;

public class JsonMoveElement extends BaseCase {

    public JsonMoveElement(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireFile()) {
            return true;
        }

        if (cp.requireArgs(parts, 3, "Usage: move <from> <to>")) {
            return true;
        }

        String fromPath = parts[1];
        String toPath = parts[2];

        if (fromPath.isEmpty() || toPath.isEmpty()) {
            System.out.println("Error: Path cannot be empty.");
            return true;
        }

        if (fromPath.equals(toPath)) {
            System.out.println("Error: Source and destination paths cannot be the same.");
            return true;
        }

        try {
            cp.getJsonElement().move(fromPath, toPath);
            System.out.println("Moved: " + fromPath + " -> " + toPath);
        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}