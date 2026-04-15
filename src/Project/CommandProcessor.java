package Project;

public class CommandProcessor {

    private Mode mode = Mode.MAIN;

    private boolean fileOpened = false;
    private String currentFilePath;
    private String currentFileName;

    private JsonElement jsonElement;

    private final OpenFile openFile = new OpenFile(this);
    private final CloseFile closeFile = new CloseFile(this);
    private final JsonSave jsonSaveFile = new JsonSave(this);
    private final JsonSaveAs jsonSaveAsFile = new JsonSaveAs(this);
    private final JsonExit jsonExitFile = new JsonExit(this);

    private final JsonValidate jsonValidate = new JsonValidate(this);
    private final JsonPrint jsonPrint = new JsonPrint(this);
    private final JsonSearchKey jsonSearchKey = new JsonSearchKey(this);
    private final JsonSetElement jsonSetElement = new JsonSetElement(this);
    private final JsonCreateElement jsonCreateElement = new JsonCreateElement(this);
    private final JsonDeleteElement jsonDeleteElement = new JsonDeleteElement(this);
    private final JsonMoveElement jsonMoveElement = new JsonMoveElement(this);
    private final JsonSaveSubtree jsonSaveSubtree = new JsonSaveSubtree(this);
    private final JsonSaveAsSubtree jsonSaveAsSubtree = new JsonSaveAsSubtree(this);

    public CommandProcessor() {
        printCommands();
    }


    public boolean process(String input) {

        if (input == null || input.isBlank()) {
            return true;
        }

        String[] parts = input.trim().split("\\s+");
        String command = normalizeCommand(parts);

        if ("exit".equals(command)) {
            return jsonExitFile.execute(input, parts);
        }

        if (mode == Mode.JSON) {
            return processJson(input, parts, command);
        }

        return processMain(input, parts, command);
    }


    private boolean processMain(String input, String[] parts, String command) {

        switch (command) {

            case "open":
                return openFile.execute(input, parts);

            case "close":
                return closeFile.execute(input, parts);

            case "save":
                return jsonSaveFile.execute(input, parts);

            case "saveas":
                return jsonSaveAsFile.execute(input, parts);

            case "json":
                if (requireFile()) {
                    mode = Mode.JSON;
                    System.out.println("Entered JSON mode");
                }
                return true;

            case "help":
                printHelp();
                return true;

            default:
                System.out.println("Unknown command");
                return true;
        }
    }


    private boolean processJson(String input, String[] parts, String command) {

        if (!requireFile()) {
            return true;
        }

        switch (command) {

            case "validate":
                return jsonValidate.execute(input, parts);

            case "print":
                return jsonPrint.execute(input, parts);

            case "search":
                return jsonSearchKey.execute(input, parts);

            case "set":
                return jsonSetElement.execute(input, parts);

            case "create":
                return jsonCreateElement.execute(input, parts);

            case "delete":
                return jsonDeleteElement.execute(input, parts);

            case "move":
                return jsonMoveElement.execute(input, parts);

            case "save":
                return jsonSaveSubtree.execute(input, parts);

            case "saveas":
                return jsonSaveAsSubtree.execute(input, parts);

            case "back":
                mode = Mode.MAIN;
                System.out.println("Back to main menu");
                return true;

            case "help":
                printJsonHelp();
                return true;

            default:
                System.out.println("Unknown JSON command. Type 'back' to leave JSON mode.");
                return true;
        }
    }


    private String normalizeCommand(String[] parts) {
        String cmd = parts[0].toLowerCase();

        if (cmd.equals("save") && parts.length > 1 && parts[1].equalsIgnoreCase("as")) {
            return "saveas";
        }

        return cmd;
    }


    public boolean requireFile() {
        if (!fileOpened) {
            System.out.println("Error: no file opened");
            return false;
        }
        return true;
    }

    public boolean requireArgs(String[] parts, int count, String message) {
        if (parts.length < count) {
            System.out.println(message);
            return false;
        }
        return true;
    }

    public void reset() {
        fileOpened = false;
        currentFilePath = null;
        currentFileName = null;
        jsonElement = null;
        mode = Mode.MAIN;
    }

    public String extractPath(String input, String[] parts, int startIndex) {
        int index = input.indexOf(parts[startIndex]);
        return input.substring(index).trim();
    }


    public boolean isFileOpened() { return fileOpened; }
    public void setFileOpened(boolean v) { fileOpened = v; }

    public String getCurrentFilePath() { return currentFilePath; }
    public void setCurrentFilePath(String p) { currentFilePath = p; }

    public String getCurrentFileName() { return currentFileName; }
    public void setCurrentFileName(String n) { currentFileName = n; }

    public JsonElement getJsonElement() { return jsonElement; }
    public void setJsonElement(JsonElement j) { jsonElement = j; }

    public Mode getMode() { return mode; }
    public void setMode(Mode m) { mode = m; }

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
        System.out.println("open <file> opens <file>");
        System.out.println("close closes currently opened file");
        System.out.println("save saves the currently open file");
        System.out.println("save as <file> saves the current file in <file>");
        System.out.println("json enter JSON operations mode");
        System.out.println("help prints this information");
        System.out.println("exit exits the program");
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
}