package Project;

import java.io.File;
import java.io.FileWriter;

public class JsonSaveAsSubtree extends BaseCase {

    public JsonSaveAsSubtree(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        if (!cp.requireArgs(parts, 3, "Usage: save as <file> [path]")) {
            return true;
        }
        String filePath = cp.extractPath(input, parts, 2);
        String output;

        try {
            if (parts.length == 3) {
                output = cp.getJsonElement().toString();
            } else {
                String subPath = parts[3];
                JsonElement subtree = cp.getJsonElement().getByPath(subPath);

                if (subtree == null) {
                    System.out.println("Error: path not found");
                    return true;
                }

                output = subtree.toString();
            }

            File file = new File(filePath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(output);
            }

            System.out.println("Successfully saved " + file.getName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}