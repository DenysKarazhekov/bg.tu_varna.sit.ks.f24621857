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
            System.out.println("Error: file already opened");
            return true;
        }

        String path = cp.extractPath(input, parts, 1);
        File file = new File(path);

        try {

            if (!file.exists()) {
                file.createNewFile();
                cp.setJsonElement(new JsonObject());
                cp.getJsonElement().save(path);
            }

            String content = Files.readString(file.toPath()).trim();

            if (content.isEmpty()) {
                cp.setJsonElement(new JsonObject());
            } else {
                JsonElement parsed = JsonElement.parse(content);
                cp.setJsonElement(parsed);
            }

            cp.setCurrentFilePath(path);
            cp.setCurrentFileName(file.getName());
            cp.setFileOpened(true);

            System.out.println("Opened: " + file.getName());

        } catch (JsonException e) {
            System.out.println("Error: invalid JSON - " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error: cannot open file");
        }

        return true;
    }
}