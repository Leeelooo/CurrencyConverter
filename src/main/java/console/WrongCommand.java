package console;

public final class WrongCommand implements Command {

    @Override
    public String execute() {
        return "Wrong input. -help for help.";
    }

}