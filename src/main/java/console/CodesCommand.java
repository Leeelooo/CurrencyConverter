package console;

import core.RateUseCase;
import vo.Currency;

import java.util.stream.Collectors;

import console.Commands.Source;

public final class CodesCommand implements Command {
    private final RateUseCase useCase;
    private final Source source;

    public CodesCommand(RateUseCase useCase, Source source) {
        this.useCase = useCase;
        this.source = source;
    }

    @Override
    public String execute() {
        return useCase.getAllRates(source)
                .keySet()
                .stream()
                .map(Currency::getCode)
                .collect(Collectors.joining(", "));
    }
}
