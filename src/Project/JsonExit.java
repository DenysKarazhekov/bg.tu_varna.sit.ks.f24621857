package Project;

public class JsonExit extends BaseCase {

    public JsonExit(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        System.out.println("Exit");
        return false;
    }
}