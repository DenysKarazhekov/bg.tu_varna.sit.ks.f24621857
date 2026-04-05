package Project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class OpenFile extends BaseCase {

    public OpenFile(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {
        if (parts.length < 2) {
            System.out.println("Usage: open <file>");
            return true;
        }

        if (cp.isFileOpened()) {
            System.out.println("Error: a file is already opened. Close it first.");
            return true;
        }

        String path = cp.extractPath(input, parts, 1);
        File file = new File(path);

        try {
            if (!file.exists()) {
                file.createNewFile();
                cp.setJsonElement(new JsonObject());
                cp.getJsonElement().save(path);
            } else {
                String content = Files.readString(file.toPath()).trim();

                if (content.isEmpty()) {
                    cp.setJsonElement(new JsonObject());
                } else {
                    try {
                        cp.setJsonElement(JsonElement.parse(content));
                    } catch (Exception e) {
                        System.out.println("Error: Invalid JSON format in file. " + e.getMessage());
                        return true;
                    }
                }
            }

            cp.setCurrentFilePath(path);
            cp.setCurrentFileName(file.getName());
            cp.setFileOpened(true);

            System.out.println("Successfully opened " + file.getName());

        } catch (IOException e) {
            System.out.println("Error: Could not open or create file.");
        }

        return true;
    }
}