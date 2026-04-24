package Project;

public class JsonPrint extends BaseCase {

    public JsonPrint(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (parts.length > 1) {
            System.out.println("Error: print command does not take arguments");
            return true;
        }

        if (!cp.requireFile()) {
            return true;
        }

        try {
            cp.getJsonElement().print();
        } catch (JsonException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}