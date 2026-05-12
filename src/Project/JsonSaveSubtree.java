package Project;

public class JsonSaveSubtree extends BaseCase {

    public JsonSaveSubtree(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        try {
            if (parts.length == 1) {
                cp.getJsonElement().save(cp.getCurrentFilePath());
                System.out.println("Successfully saved " + cp.getCurrentFileName());
            } else {
                String subPath = cp.extractPath(input, parts, 1);
                cp.getJsonElement().save(cp.getCurrentFilePath(), subPath);
                System.out.println("Successfully saved subtree: " + subPath);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}