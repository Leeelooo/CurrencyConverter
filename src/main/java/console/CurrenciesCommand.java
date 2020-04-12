package console;

import core.RateUseCase;

import java.util.stream.Collectors;

import console.Commands.Source;

public final class CurrenciesCommand implements Command {
    private final RateUseCase useCase;
    private final Source source;

    public CurrenciesCommand(RateUseCase useCase, Source source) {
        this.useCase = useCase;
        this.source = source;
    }

    @Override
    public String execute() {
        return useCase.getAllRates(source)
                .entrySet()
                .stream()
                .map(pair -> "1EUR = " + pair.getValue() + pair.getKey().getCode())
                .collect(Collectors.joining(", "));
    }
}
