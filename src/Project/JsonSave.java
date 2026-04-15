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
            System.out.println("Saved: " + cp.getCurrentFileName());
        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: Cannot save file");
        }

        return true;
    }
}