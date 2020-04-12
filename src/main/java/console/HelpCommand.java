package console;

public final class HelpCommand implements Command {
    @Override
    public String execute() {
        return "-convert -from_amount amount -from_code from_code [-to_code code] " +
                "- converting amount from from_code currency to code currency.\n" +
                "-update - force update local info.\n" +
                "-codes - prints available currencies' codes.\n" +
                "-currencies - prints available currencies.\n" +
                "-help - this message.";
    }
}
