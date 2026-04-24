package Project;

public class JsonValidate extends BaseCase {

    public JsonValidate(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (parts.length > 1) {
            System.out.println("Error: validate command does not take arguments");
            return true;
        }

        if (!cp.requireFile()) {
            return true;
        }

        try {
            cp.getJsonElement().validate();
            System.out.println("JSON is valid");
        } catch (JsonException e) {
            System.out.println("Invalid JSON: " + e.getMessage());
        }

        return true;
    }
}