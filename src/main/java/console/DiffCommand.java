package console;

import java.util.Map;
import java.util.stream.Collectors;

import console.Commands.Source;
import core.RateUseCase;
import vo.Currency;

public final class DiffCommand implements Command {
    private final RateUseCase useCase;

    public DiffCommand(RateUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public String execute() {
        Map<Currency, Double> local = useCase.getAllRates(Source.LOCAL);
        return useCase.getAllRates(Source.REMOTE)
                .entrySet()
                .stream()
                .filter(it -> local.get(it.getKey()) != it.getValue())
                .map(it -> it.getKey() + " = " + (it.getValue() - local.get(it.getKey())))
                .collect(Collectors.joining(", "));
    }

    

}