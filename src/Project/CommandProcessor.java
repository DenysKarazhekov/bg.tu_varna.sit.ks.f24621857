package Project;

public class CommandProcessor {

    private Mode mode = Mode.MAIN;

    private boolean fileOpened = false;
    private String currentFilePath = null;
    private String currentFileName = null;

    private JsonElement jsonElement = null;

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

        if (input == null || input.trim().isEmpty()) {
            return true;
        }

        String trimmedInput = input.trim();
        String[] parts = trimmedInput.split("\\s+");
        String command = parts[0].toLowerCase();

        if (command.equals("save") && parts.length > 1 && parts[1].equalsIgnoreCase("as")) {
            command = "saveas";
        }

        if (command.equals("exit")) {
            return jsonExitFile.execute(trimmedInput, parts);
        }

        if (mode == Mode.JSON) {
            return processJsonCommands(trimmedInput, parts, command);
        }

        switch (command) {

            case "open":
                return openFile.execute(trimmedInput, parts);
            case "close":
                return closeFile.execute(trimmedInput, parts);
            case "save":
                return jsonSaveFile.execute(trimmedInput, parts);
            case "saveas":
                return jsonSaveAsFile.execute(trimmedInput, parts);
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
                return jsonExitFile.execute(trimmedInput, parts);
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
                break;

            case "help":
                printJsonHelp();
                break;

            default:
                System.out.println("Unknown JSON command. Type 'back' to leave JSON mode.");
        }
        return true;
    }

    public boolean isFileOpened() {
        return fileOpened;
    }
    public void setFileOpened(boolean value) {
        fileOpened = value;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }
    public void setCurrentFilePath(String path) {
        currentFilePath = path;
    }

    public String getCurrentFileName() {
        return currentFileName;
    }
    public void setCurrentFileName(String name) {
        currentFileName = name;
    }

    public JsonElement getJsonElement() {
        return jsonElement;
    }
    public void setJsonElement(JsonElement json) {
        jsonElement = json;
    }

    public Mode getMode() {
        return mode;
    }
    public void setMode(Mode mode) {
        this.mode = mode;
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