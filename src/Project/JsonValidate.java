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
            System.out.println("Valid JSON");
        } catch (JsonException e) {
            System.out.println("Invalid JSON: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid JSON");
        }

        return true;
    }
}