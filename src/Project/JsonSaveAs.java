package Project;

import java.io.File;

public class JsonSaveAs extends BaseCase {

    public JsonSaveAs(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireArgs(parts, 3, "Usage: save as <file>")) {
            return true;
        }

        try {
            String newFilePath = cp.extractPath(input, parts, 2);

            File file = new File(newFilePath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            cp.getJsonElement().save(newFilePath);
            System.out.println("Successfully saved " + file.getName());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}