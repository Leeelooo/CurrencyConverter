package console;

import core.RateUseCase;
import vo.Amount;
import vo.Currency;

public final class ConvertCommand implements Command {
    private final RateUseCase useCase;
    private final Currency to;
    private final Amount from;

    public ConvertCommand(RateUseCase useCase, Currency to, Amount from) {
        this.useCase = useCase;
        this.to = to;
        this.from = from;
    }

    @Override
    public String execute() {
        Amount converted = new Amount(
                to,
                from.getAmount() / useCase.getRate(from.getCurrency()) * useCase.getRate(to)
        );
        return from.getAmount() + from.getCurrency().getCode() + " = " +
                converted.getAmount() + converted.getCurrency().getCode();
    }
}
