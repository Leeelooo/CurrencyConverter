package console;

import console.Commands.Source;
import core.RateUseCase;
import vo.Amount;
import vo.Currency;

public final class ConvertCommand implements Command {
    private final RateUseCase useCase;
    private final Source source;
    private final Currency to;
    private final Amount from;

    public ConvertCommand(RateUseCase useCase, Currency to, Amount from, Source source) {
        this.useCase = useCase;
        this.to = to;
        this.from = from;
        this.source = source;
    }

    @Override
    public String execute() {
        Amount converted = new Amount(
                to,
                from.getAmount() / useCase.getRate(from.getCurrency(), source) * useCase.getRate(to, source)
        );
        return from.getAmount() + from.getCurrency().getCode() + " = " +
                converted.getAmount() + converted.getCurrency().getCode();
    }
}
