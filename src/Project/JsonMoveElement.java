package Project;

public class JsonMoveElement extends BaseCase {

    public JsonMoveElement(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireArgs(parts, 3, "Usage: move <from> <to>")) {
            return true;
        }

        String from = parts[1];
        String to = parts[2];

        try {
            cp.getJsonElement().move(from, to);
            System.out.println("Successfully moved " + from + " -> " + to);
        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}