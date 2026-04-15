package Project;

public class JsonPrint extends BaseCase {

    public JsonPrint(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireFile()) {
            return true;
        }

        try {
            System.out.println("JSON:");
            cp.getJsonElement().print();
        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}