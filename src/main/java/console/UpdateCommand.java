package console;

import core.RateUseCase;

public final class UpdateCommand implements Command {
    private final RateUseCase useCase;

    public UpdateCommand(RateUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public String execute() {
        useCase.updateRates();
        return "Updated!";
    }
}
