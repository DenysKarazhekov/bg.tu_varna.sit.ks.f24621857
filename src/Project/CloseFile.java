package Project;

public class CloseFile extends BaseCase {

    public CloseFile(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        if (!cp.isFileOpened()) {
            System.out.println("Error: no file opened");
            return true;
        }

        System.out.println("Successfully closed " + cp.getCurrentFileName());
        cp.reset();

        return true;
    }
}