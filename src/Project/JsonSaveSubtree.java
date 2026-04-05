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
            if (parts.length == 1) {
                cp.getJsonElement().save(cp.getCurrentFilePath());
                System.out.println("Full JSON successfully saved to " + cp.getCurrentFileName());
            }
             else {
                String jsonSubPath = parts[1];
                cp.getJsonElement().save(cp.getCurrentFilePath(), jsonSubPath);
                System.out.println("Subtree [" + jsonSubPath + "] successfully saved to " + cp.getCurrentFileName());
            }
        } catch (Exception e) {
            System.out.println("Error: Could not save to " + cp.getCurrentFileName() + ". " + e.getMessage());
        }

        return true;
    }
}