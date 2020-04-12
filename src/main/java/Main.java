import console.Commands;
import core.RateUseCase;

public class Main {

    public static void main(String[] args) {
        System.out.println(Commands.of(RateUseCase.get(), args).execute());
    }

}