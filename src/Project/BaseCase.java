package Project;

public abstract class BaseCase implements Command {

    protected final CommandProcessor cp;

    public BaseCase(CommandProcessor cp) {
        this.cp = cp;
    }

    @Override
    public abstract boolean execute(String input, String[] parts);
}