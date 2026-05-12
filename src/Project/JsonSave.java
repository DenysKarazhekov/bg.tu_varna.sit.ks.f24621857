package Project;

import java.io.FileWriter;
import java.io.IOException;

public class JsonSave extends BaseCase {

    public JsonSave(CommandProcessor cp) {
        super(cp);
    }

    @Override
    public boolean execute(String input, String[] parts) {

        if (parts.length > 1) {
            System.out.println("Error: save command does not take arguments");
            return true;
        }

        try (FileWriter writer = new FileWriter(cp.getCurrentFilePath())) {
            writer.write(cp.getJsonElement().toString());
            System.out.println("Successfully saved " + cp.getCurrentFileName());
        } catch (IOException e) {
            System.out.println("Error: cannot save file");
        }

        return true;
    }
}