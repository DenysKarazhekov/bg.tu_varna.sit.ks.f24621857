package Project;

public class JsonSaveAs extends BaseCase {

    public JsonSaveAs(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireFile()) {
            return true;
        }

        if (!cp.requireArgs(parts, 3, "Usage: save as <file>")) {
            return true;
        }

        String newFilePath = cp.extractPath(input, parts, 2);

        if (newFilePath == null || newFilePath.isBlank()) {
            System.out.println("Error: Invalid file path");
            return true;
        }

        try {
            cp.getJsonElement().save(newFilePath);
            System.out.println("Saved as: " + newFilePath);

        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Could not save file");
        }

        return true;
    }
}