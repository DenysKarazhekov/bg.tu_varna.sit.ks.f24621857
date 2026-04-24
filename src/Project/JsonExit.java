package Project;

public class JsonExit extends BaseCase {

    public JsonExit(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (parts.length > 1) {
            System.out.println("Error: exit command does not take arguments");
            return true;
        }

        System.out.println("Exiting the program...");
        return false;
    }
}