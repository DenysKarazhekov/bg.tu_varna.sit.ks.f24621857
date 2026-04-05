package Project;

public class JsonPrint extends BaseCase {

    public JsonPrint(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        if (cp.requireFile()) {
            try {
                System.out.println("--- JSON Content ---");

                cp.getJsonElement().print();

                System.out.println("\n--------------------");
            } catch (Exception e) {
                System.out.println("Error while printing JSON: " + e.getMessage());
            }
        }
        return true;
    }
}