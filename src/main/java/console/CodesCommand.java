package console;

import core.RateUseCase;
import vo.Currency;

import java.util.stream.Collectors;

public final class CodesCommand implements Command {
    private final RateUseCase useCase;

    public CodesCommand(RateUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public String execute() {
        return useCase.getAllRates()
                .keySet()
                .stream()
                .map(Currency::getCode)
                .collect(Collectors.joining(", "));
    }
}
