package console;

import core.RateUseCase;

import java.util.stream.Collectors;

public final class CurrenciesCommand implements Command {
    private final RateUseCase useCase;

    public CurrenciesCommand(RateUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public String execute() {
        return useCase.getAllRates()
                .entrySet()
                .stream()
                .map(pair -> "1EUR = " + pair.getValue() + pair.getKey().getCode())
                .collect(Collectors.joining(", "));
    }
}
