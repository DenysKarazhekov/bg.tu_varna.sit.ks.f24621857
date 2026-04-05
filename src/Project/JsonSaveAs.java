package Project;

import java.io.File;

public class JsonSaveAs extends BaseCase {

    public JsonSaveAs(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        if (!cp.requireFile() || !cp.requireArgs(parts, 3, "Usage: save as <file>")) {
            return true;
        }

        String newFilePath = cp.extractPath(input, parts, 2);

        try {
            cp.getJsonElement().save(newFilePath);
            System.out.println("Successfully saved as " + new File(newFilePath).getName());

        } catch (Exception e) {
            System.out.println("Error: Could not save file to " + newFilePath);
        }

        return true;
    }
}