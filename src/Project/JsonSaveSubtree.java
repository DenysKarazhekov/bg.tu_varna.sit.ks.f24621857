package Project;

public class JsonSaveSubtree extends BaseCase {

    public JsonSaveSubtree(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireFile()) {
            return true;
        }

        try {
            if (parts.length < 2) {
                cp.getJsonElement().save(cp.getCurrentFilePath());
                System.out.println("Saved full JSON: " + cp.getCurrentFileName());
                return true;
            }
            String jsonSubPath = parts[1];

            if (jsonSubPath == null || jsonSubPath.isBlank()) {
                System.out.println("Error: invalid path");
                return true;
            }

            cp.getJsonElement().save(cp.getCurrentFilePath(), jsonSubPath);
            System.out.println("Saved subtree: " + jsonSubPath);

        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Could not save file");
        }

        return true;
    }
}