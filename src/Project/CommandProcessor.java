package Project;

import java.io.*;
import java.nio.file.Files;

public class CommandProcessor {

    private Mode mode = Mode.MAIN;

    private boolean fileOpened = false;
    private String currentFilePath = null;
    private String currentFileName = null;

    private JsonElement jsonElement = null;

    public CommandProcessor() {
        printCommands();
    }

    public boolean process(String input) {

        if (input == null || input.trim().isEmpty()) {
            return true;
        }

        String trimmedInput = input.trim();
        String[] parts = trimmedInput.split("\\s+");
        String command = parts[0].toLowerCase();

        if (command.equals("save") && parts.length > 1 && parts[1].equalsIgnoreCase("as")) {
            command = "saveas";
        }

        if (mode == Mode.JSON) {
            return processJsonCommands(trimmedInput, parts, command);
        }

        switch (command) {

            case "open":
                if (!requireArgs(parts, 2, "Usage: open <file>")) {
                    return true;
                }
                if (fileOpened) {
                    System.out.println("Error: file already opened");
                    return true;
                }

                String path = extractPath(trimmedInput, parts, 1);
                File file = new File(path);

                try {
                    if (!file.exists()) {
                        file.createNewFile();
                        jsonElement = new JsonObject();
                        jsonElement.save(path);
                    } else {
                        String content = Files.readString(file.toPath()).trim();

                        if (content.isEmpty()) {
                            jsonElement = new JsonObject();
                        }
                        else {
                            jsonElement = new JsonObject();
                        }
                    }

                    currentFilePath = path;
                    currentFileName = file.getName();
                    fileOpened = true;

                    System.out.println("Successfully opened " + currentFileName);

                } catch (IOException e) {
                    System.out.println("Error: Could not open file");
                }
                break;

            case "close":
                if (requireFile()) {
                    System.out.println("Successfully closed " + currentFileName);
                    reset();
                }
                break;

            case "save":
                if (requireFile()) {
                    try {
                        jsonElement.save(currentFilePath);
                        System.out.println("Successfully saved " + currentFileName);
                    } catch (Exception e) {
                        System.out.println("Error: Could not save file");
                    }
                }
                break;

            case "saveas":
                if (requireFile() && requireArgs(parts, 3, "Usage: save as <file>")) {
                    String newPath = extractPath(trimmedInput, parts, 2);
                    try {
                        jsonElement.save(newPath);
                        System.out.println("Successfully saved " + new File(newPath).getName());
                    } catch (Exception e) {
                        System.out.println("Error: Could not save file");
                    }
                }
                break;

            case "json":
                if (requireFile()) {
                    mode = Mode.JSON;
                    System.out.println("Entered JSON mode");
                }
                break;

            case "help":
                printHelp();
                break;

            case "exit":
                System.out.println("Exiting the program...");
                return false;

            default:
                System.out.println("Unknown command");
        }

        return true;
    }

    private boolean processJsonCommands(String input, String[] parts, String command) {
        if (!requireFile()) {
            return true;
        }

        switch (command) {

            case "validate":
                try {
                    jsonElement.toString();
                    System.out.println("JSON is valid");
                } catch (Exception e) {
                    System.out.println("Invalid JSON format");
                }
                break;

            case "print":
                jsonElement.print();
                break;

            case "search":
                if (requireArgs(parts, 2, "Usage: search <key>")) {
                    jsonElement.search(parts[1]);
                }
                break;

            case "set":
                if (requireArgs(parts, 3, "Usage: set <path> <value>")) {
                    int index = input.indexOf(parts[1]) + parts[1].length();
                    String value = input.substring(index).trim();
                    jsonElement.set(parts[1], value);
                }
                break;

            case "create":
                if (requireArgs(parts, 3, "Usage: create <path> <value>")) {
                    int index = input.indexOf(parts[1]) + parts[1].length();
                    String value = input.substring(index).trim();
                    jsonElement.create(parts[1], value);
                }
                break;

            case "delete":
                if (requireArgs(parts, 2, "Usage: delete <path>")) {
                    jsonElement.delete(parts[1]);
                }
                break;

            case "move":
                if (requireArgs(parts, 3, "Usage: move <from> <to>")) {
                    jsonElement.move(parts[1], parts[2]);
                }
                break;

            case "save":
                try {
                    if (parts.length == 1) {
                        jsonElement.save(currentFilePath);
                    } else {
                        jsonElement.save(parts[1]);
                    }
                    System.out.println("Successfully saved");
                } catch (Exception e) {
                    System.out.println("Error: Could not save");
                }
                break;

            case "saveas":
                if (requireArgs(parts, 3, "Usage: save as <file> [path]")) {
                    String newPath = extractPath(input, parts, 2);

                    try {
                        if (parts.length > 3) {
                            jsonElement.saveAs(newPath, parts[3]);
                        } else {
                            jsonElement.save(newPath);
                        }
                        System.out.println("Successfully saved " + new File(newPath).getName());
                    } catch (Exception e) {
                        System.out.println("Error: Could not save");
                    }
                }
                break;

            case "back":
                mode = Mode.MAIN;
                System.out.println("Back to main menu");
                break;

            case "help":
                printJsonHelp();
                break;

            case "exit":
                System.out.println("Exiting the program...");
                return false;

            default:
                System.out.println("Unknown JSON command. Type 'back' to leave JSON mode.");
        }

        return true;
    }

    private void printCommands() {
        System.out.println("Main commands:");
        System.out.println("open <file>");
        System.out.println("close");
        System.out.println("save");
        System.out.println("save as <file>");
        System.out.println("json");
        System.out.println("help");
        System.out.println("exit");
    }

    private void printHelp() {
        System.out.println("The following commands are supported:");
        System.out.println("open <file>      opens <file>");
        System.out.println("close            closes currently opened file");
        System.out.println("save             saves the currently open file");
        System.out.println("save as <file>   saves the current file in <file>");
        System.out.println("json             enter JSON operations mode");
        System.out.println("help             prints this information");
        System.out.println("exit             exits the program");
    }

    private void printJsonHelp() {
        System.out.println("JSON mode commands:");
        System.out.println("validate");
        System.out.println("print");
        System.out.println("search <key>");
        System.out.println("set <path> <value>");
        System.out.println("create <path> <value>");
        System.out.println("delete <path>");
        System.out.println("move <from> <to>");
        System.out.println("save [path]");
        System.out.println("save as <file> [path]");
        System.out.println("back");
    }

    private boolean requireFile() {
        if (!fileOpened) {
            System.out.println("Error: no file opened");
            return false;
        }
        return true;
    }

    private boolean requireArgs(String[] parts, int count, String message) {
        if (parts.length < count) {
            System.out.println(message);
            return false;
        }
        return true;
    }

    private void reset() {
        fileOpened = false;
        currentFilePath = null;
        currentFileName = null;
        jsonElement = null;
        mode = Mode.MAIN;
    }

    private String extractPath(String input, String[] parts, int startIndex) {
        int index = input.indexOf(parts[startIndex]);
        return input.substring(index).trim();
    }
}