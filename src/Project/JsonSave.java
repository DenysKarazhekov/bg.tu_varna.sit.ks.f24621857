package Project;


public class JsonSave extends BaseCase {

    public JsonSave(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        if (!cp.requireFile()) {
            return true;
        }

        try {
            cp.getJsonElement().save(cp.getCurrentFilePath());

            System.out.println("Successfully saved " + cp.getCurrentFileName());
        } catch (Exception e) {
            System.out.println("Error: Could not save file. " + e.getMessage());
        }

        return true;
    }
}