package Project;

public class CloseFile extends BaseCase {

    public CloseFile(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.isFileOpened()) {
            System.out.println("Error: no file is opened");
            return true;
        }

        String fileName = cp.getCurrentFileName();

        cp.reset();

        System.out.println("Closed: " + fileName);

        return true;
    }
}