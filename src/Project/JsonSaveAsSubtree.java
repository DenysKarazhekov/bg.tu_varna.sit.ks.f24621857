package Project;

import java.io.File;

public class JsonSaveAsSubtree extends BaseCase {

    public JsonSaveAsSubtree(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
       if (!cp.requireFile() || !cp.requireArgs(parts, 3, "Usage: save as <file> [path]")) {
            return true;
        }

        String newFilePath = parts[2];

        try {
            if (parts.length > 3) {
                String jsonSubPath = parts[3];
                cp.getJsonElement().saveAs(newFilePath, jsonSubPath);
                System.out.println("Subtree [" + jsonSubPath + "] successfully saved to " + new File(newFilePath).getName());
            }
            else {
                cp.getJsonElement().save(newFilePath);
                System.out.println("Full JSON successfully saved as " + new File(newFilePath).getName());
            }
        } catch (Exception e) {
            System.out.println("Error: Could not save to " + newFilePath + ". " + e.getMessage());
        }

        return true;
    }
}