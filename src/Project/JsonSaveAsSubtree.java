package Project;

public class JsonSaveAsSubtree extends BaseCase {

    public JsonSaveAsSubtree(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireFile()) {
            return true;
        }

        if (!cp.requireArgs(parts, 3, "Usage: save as <file> [path]")) {
            return true;
        }

        String newFilePath = parts[2];

        try {
            if (parts.length > 3) {
                String jsonSubPath = cp.extractPath(input, parts, 3);

                if (jsonSubPath == null || jsonSubPath.isBlank()) {
                    System.out.println("Error: Invalid path");
                    return true;
                }

                cp.getJsonElement().saveAs(newFilePath, jsonSubPath);
                System.out.println("Saved subtree: " + jsonSubPath);
            }
            else {
                cp.getJsonElement().save(newFilePath);
                System.out.println("Saved full JSON: " + newFilePath);
            }

        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Could not save file");
        }

        return true;
    }
}