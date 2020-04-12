package console;

import core.RateUseCase;
import vo.Amount;
import vo.Currency;

public final class Commands {

    private enum Option {
        CONVERT,
        UPDATE,
        CODES,
        CURRENCIES,
        HELP,
        WRONG
    }

    public enum Source {
        REMOTE,
        LOCAL
    }

    public static Command of(RateUseCase useCase, String... options) {
        Option option = Option.WRONG;
        Source source = Source.LOCAL;

        String toCode = null;
        String fromCode = null;
        Double fromAmount = null;

        loop:
        for (int i = 0; i < options.length; i++) {
            switch (options[i]) {
                case "-convert":
                    if (option != Option.WRONG) {
                        return new WrongCommand();
                    }
                    option = Option.CONVERT;
                    break;
                case "-update":
                    if (option != Option.WRONG) {
                        return new WrongCommand();
                    }
                    option = Option.UPDATE;
                    break;
                case "-codes":
                    if (option != Option.WRONG) {
                        return new WrongCommand();
                    }
                    option = Option.CODES;
                    break;
                case "-currencies":
                    if (option != Option.WRONG) {
                        return new WrongCommand();
                    }
                    option = Option.CURRENCIES;
                    break;
                case "-help":
                    if (option != Option.WRONG) {
                        return new WrongCommand();
                    }
                    option = Option.HELP;
                    break;
                case "-from_amount":
                    fromAmount = Double.parseDouble(options[++i]);
                    break;
                case "-from_code":
                    fromCode = options[++i];
                    break;
                case "-to_code":
                    toCode = options[++i];
                    break;
                case "-source":
                    switch (options[++i]) {
                        case "local":
                            source = Source.LOCAL; 
                            break;
                        case "remote":
                            source = Source.REMOTE;
                            break;
                        default:
                            return new WrongCommand();
                    }
                    break;
                default:
                    option = Option.WRONG;
                    break loop;
            }
        }

        switch (option) {
            case CONVERT:
                if (!useCase.containsCurrency(fromCode)
                        || !useCase.containsCurrency(toCode)
                        || fromAmount == null
                        || fromAmount < 0) {
                    return new WrongCommand();
                }
                Amount amount = new Amount(fromCode, fromAmount);
                Currency currency = new Currency(toCode);
                return new ConvertCommand(useCase, currency, amount, source);
            case UPDATE:
                if (fromAmount != null || fromCode != null || toCode != null) {
                    return new WrongCommand();
                }
                return new UpdateCommand(useCase);
            case CODES:
                if (fromAmount != null || fromCode != null || toCode != null) {
                    return new WrongCommand();
                }
                return new CodesCommand(useCase, source);
            case CURRENCIES:
                if (fromAmount != null || fromCode != null || toCode != null) {
                    return new WrongCommand();
                }
                return new CurrenciesCommand(useCase, source);
            case HELP:
                if (fromAmount != null || fromCode != null || toCode != null) {
                    return new WrongCommand();
                }
                return new HelpCommand();
            case WRONG:
                return new WrongCommand();
        }
        return new WrongCommand();
    }

}
