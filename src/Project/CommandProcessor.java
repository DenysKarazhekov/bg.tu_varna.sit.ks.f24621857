package Project;

public class CommandProcessor {

    private boolean fileOpened = false;
    private String currentFilePath;
    private String currentFileName;
    private JsonElement jsonElement;
    private boolean jsonMode = false;

    private final OpenFile openFile = new OpenFile(this);
    private final CloseFile closeFile = new CloseFile(this);
    private final JsonSave jsonSave = new JsonSave(this);
    private final JsonSaveAs jsonSaveAs = new JsonSaveAs(this);
    private final JsonExit jsonExit = new JsonExit(this);
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
        if (input == null) {
            return true;
        }

        String[] parts = input.trim().split("\\s+");
        String command = normalizeCommand(parts);

        if ("exit".equals(command)) {
            return jsonExit.execute(input, parts);
        }

        if (jsonMode) {
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
                return jsonSave.execute(input, parts);
            case "saveas":
                return jsonSaveAs.execute(input, parts);
            case "json":
                if (requireFile()) {
                    jsonMode = true;
                    Main.clearConsole();
                    printJsonHelp();
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
                jsonMode = false;
                Main.clearConsole();
                printCommands();
                return true;
            case "help":
                printJsonHelp();
                return true;
            default:
                System.out.println("Unknown JSON command");
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

    public String extractPath(String input, String[] parts, int startIndex) {
        int pos = 0;
        for (int i = 0; i < startIndex; i++) {
            pos = input.indexOf(' ', pos) + 1;
        }
        String result = input.substring(pos).trim();
        if (result.startsWith("\"") && result.endsWith("\"")) {
            result = result.substring(1, result.length() - 1);
        }
        return result;
    }

    public void reset() {
        fileOpened = false;
        currentFilePath = null;
        currentFileName = null;
        jsonElement = null;
        jsonMode = false;
    }

    public boolean isFileOpened(){
        return fileOpened;
    }
    public void setFileOpened(boolean v){
        fileOpened = v;
    }
    public String getCurrentFilePath(){
        return currentFilePath;
    }
    public void setCurrentFilePath(String p){
        currentFilePath = p;
    }
    public String getCurrentFileName(){
        return currentFileName;
    }
    public void setCurrentFileName(String n){
        currentFileName = n;
    }
    public JsonElement getJsonElement(){
        return jsonElement;
    }
    public void setJsonElement(JsonElement j){
        jsonElement = j;
    }

    public boolean isJsonMode(){
        return jsonMode;
    }

    public void printCommands() {
        System.out.println("        Main commands:");
        System.out.println("- open <file>");
        System.out.println("- close");
        System.out.println("- save");
        System.out.println("- save as <file>");
        System.out.println("- json command");
        System.out.println("- help");
        System.out.println("- exit");
    }
    public void printHelp() {
        System.out.println("    The following commands are supported:");
        System.out.println("open <file>    -   opens <file>");
        System.out.println("close          -   closes currently opened file");
        System.out.println("save           -   saves the currently open file");
        System.out.println("save as <file> -   saves the current file in <file>");
        System.out.println("json           -   enter JSON operations mode");
        System.out.println("help           -   prints this information");
        System.out.println("exit           -   exits the program");
    }
    public void printJsonHelp() {
        System.out.println("   JSON mode commands:");
        System.out.println("- validate");
        System.out.println("- print");
        System.out.println("- search <key>");
        System.out.println("- set <path> <value>");
        System.out.println("- create <path> <value>");
        System.out.println("- delete <path>");
        System.out.println("- move <from> <to>");
        System.out.println("- save [path]");
        System.out.println("- save as <file> [path]");
        System.out.println("- back");
    }
}