public class Main {

    private enum Option {
        CONVERT,
        UPDATE,
        CODES,
        CURRENCIES,
        HELP,
        WRONG
    }

    public static void main(String[] args) {
        Option option = Option.WRONG;
        String toCode = null;
        String fromCode = null;
        Double fromAmount = null;

        loop: for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-convert":
                    option = Option.CONVERT;
                    break;
                case "-update":
                    option = Option.UPDATE;
                    break;
                case "-codes":
                    option = Option.CODES;
                    break;
                case "-currencies":
                    option = Option.CURRENCIES;
                    break;
                case "-help":
                    option = Option.HELP;
                    break;
                case "-from_amount":
                    fromAmount = Double.parseDouble(args[++i]);
                    break;
                case "-from_code":
                    fromCode = args[++i];
                    break;
                case "-to_code":
                    toCode = args[++i];
                    break;
                default:
                    option = Option.WRONG;
                    break loop;
            }
        }
        switch (option) {
            case CONVERT:
                convertAmount(fromCode, fromAmount, toCode);
                break;
            case UPDATE:
                requestUpdate();
                break;
            case CODES:
                printAllCodes();
                break;
            case CURRENCIES:
                printAllCurrencies();
                break;
            case HELP:
                printHelpMessage();
                break;
            default:
                printWrongOption();
                break;
        }
    }

    private static void convertAmount(String fromCode, Double fromAmount, String toCode) {
        System.out.println(CurrencyConverter.convertTo(fromCode, fromAmount, toCode).toString());
    }

    private static void requestUpdate() {
        CurrencyConverter.updateRates();
    }

    private static void printAllCodes() {
        CurrencyConverter
                    .getAllCodes()
                    .forEach(System.out::println);
    }

    private static void printAllCurrencies() {
        CurrencyConverter
                    .getRates()
                    .forEach((k, v) -> {
                        System.out.println("1 EUR = " + v + " " + k.getCode());
                    });
    }

    private static void printHelpMessage() {
        System.out.println("-convert -from_amount amount -from_code from_code [-to_code code] " 
                    + "- converting amount from from_code currency to code currency.");
        System.out.println("-update - force update local info.");
        System.out.println("-codes - prints available currencies' codes.");
        System.out.println("-currencies - prints available currencies.");
        System.out.println("-help - this message.");
    }

    private static void printWrongOption() {
        System.out.println("Wrong input. -help for help.");
    }

}