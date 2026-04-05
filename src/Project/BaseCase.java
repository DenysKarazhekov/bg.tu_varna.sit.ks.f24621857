package Project;
import Project.*;

public abstract class BaseCase implements  Command {

    protected CommandProcessor cp;

    public BaseCase(CommandProcessor cp) {
        this.cp = cp;
    }

    public abstract boolean execute(String input, String[] parts);
}