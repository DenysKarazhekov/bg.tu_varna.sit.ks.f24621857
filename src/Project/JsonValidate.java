package Project;

public class JsonValidate extends BaseCase {

    public JsonValidate(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        if (!cp.requireFile()) {
            return true;
        }

        try {
            cp.getJsonElement().validate();
            System.out.println("JSON is valid.");
        } catch (Exception e) {
            System.out.println("Invalid JSON format: " + e.getMessage());
        }

        return true;
    }
}