package Project;

import java.io.File;
import java.io.FileWriter;
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
            System.out.println("Error: file already opened");
            return true;
        }

        String path = cp.extractPath(input, parts, 1).replace("\"", "").trim();
        File file = new File(path).getAbsoluteFile();

        try {
            if (!file.exists()) {
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }

                file.createNewFile();

                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("{}");
                }

                cp.setJsonElement(new JsonObject());
                cp.setFileOpened(true);
                cp.setCurrentFilePath(file.getAbsolutePath());
                cp.setCurrentFileName(file.getName());

                System.out.println("Created new JSON file: " + file.getName());
                return true;
            }

            String content = Files.readString(file.toPath()).replace("\uFEFF", "").trim();

            if (content.isEmpty()) {
                cp.setJsonElement(new JsonObject());
            } else {
                cp.setJsonElement(JsonElement.parse(content));
            }

            cp.setFileOpened(true);
            cp.setCurrentFilePath(file.getAbsolutePath());
            cp.setCurrentFileName(file.getName());

            System.out.println("Successfully opened " + file.getName());

        } catch (JsonException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return true;
    }
}