package Project;

public class CloseFile extends BaseCase {

    public CloseFile(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (parts.length > 1) {
            System.out.println("Error: close command does not take arguments");
            return true;
        }

        if (!cp.isFileOpened()) {
            System.out.println("Error: no file is opened");
            return true;
        }

        String fileName = cp.getCurrentFileName();

        cp.reset();

        System.out.println("Successfully closed " + fileName);

        return true;
    }
}