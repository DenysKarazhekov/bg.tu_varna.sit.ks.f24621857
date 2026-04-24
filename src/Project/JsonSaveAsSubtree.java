package Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsonSaveAsSubtree extends BaseCase {

    public JsonSaveAsSubtree(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (!cp.requireFile()) {
            return true;
        }

        if (parts.length < 3) {
            System.out.println("Usage: save as <file> [path]");
            return true;
        }

        String path = parts[2];

        try {

            String output;

            if (parts.length == 3) {
                output = cp.getJsonElement().toString();
                System.out.println("Saved full JSON");
            }

            else {
                String subPath = parts[3];

                JsonElement subtree = cp.getJsonElement().findPath(subPath);

                if (subtree == null) {
                    System.out.println("Error: path not found");
                    return true;
                }

                output = subtree.toString();
                System.out.println("Saved subtree: " + subPath);
            }

            writeFile(path, output);

        } catch (Exception e) {
            System.out.println("Error writing file: " + e.getMessage());
        }

        return true;
    }

    private void writeFile(String path, String content) throws IOException {

        File file = new File(path);

        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }

        System.out.println("File written: " + file.getAbsolutePath());
    }
}