package core;

import utils.Config;
import vo.Amount;
import vo.Currency;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrencyConverter {
    private static RateUseCase useCase;

    /**
     * Converting amount to another currency.
     *
     * @param fromCode   currency code for converting. Null for base code.
     * @param fromAmount amount of money to be converted.
     * @param toCode     currency code to be converted from. Null for base code.
     * @return           converted amount to @param toCode. Returning null if @param fromAmount is null.
     * @see Amount
     */
    public static Amount convertTo(String fromCode, Double fromAmount, String toCode) {
        if (useCase == null)
            useCase = new RateUseCaseImpl(new LocalRateDataSource(), new RemoteRateDataSource());
        if (fromAmount == null) {
            System.out.println("Amount not specified");
            return null;
        }

        Currency amountCurrency;
        Currency currency;
        if (fromCode == null) {
            System.out.println("From code not specified - using base code(" + Config.BASE_CURRENCY_CODE + ")");
            amountCurrency = new Currency(Config.BASE_CURRENCY_CODE);
        } else if (getAllCodes().contains(fromCode) || fromCode.equals(Config.BASE_CURRENCY_CODE)) {
            amountCurrency = new Currency(fromCode);
        } else {
            System.out.println("Unknown code " + fromCode);
            return null;
        }
        if (toCode == null) {
            System.out.println("To code not specified - using base code(" + Config.BASE_CURRENCY_CODE + ")");
            currency = new Currency(Config.BASE_CURRENCY_CODE);
        } else if (getAllCodes().contains(toCode) || toCode.equals(Config.BASE_CURRENCY_CODE)) {
            currency = new Currency(toCode);
        } else {
            System.out.println("Unknown code " + toCode);
            return null;
        }

        Amount amount = new Amount(amountCurrency, fromAmount);
        return new Amount(currency, amount.getAmount() / useCase.getRate(amount.getCurrency()) * useCase.getRate(currency));
    }

    /**
     * Updates local cache.
     */
    public static void updateRates() {
        if (useCase == null)
            useCase = new RateUseCaseImpl(new LocalRateDataSource(), new RemoteRateDataSource());
        useCase.updateRates();
    }

    /**
     * Returns the list of currencies' codes.
     *
     * @return List of available currencies' codes.
     */
    public static List<String> getAllCodes() {
        if (useCase == null)
            useCase = new RateUseCaseImpl(new LocalRateDataSource(), new RemoteRateDataSource());
        return useCase.getAllRates().keySet()
                .stream()
                .map(Currency::getCode)
                .collect(Collectors.toList());
    }

    /**
     * Returns the pairs of currency code and it rate to base currency.
     *
     * @return list of pairs of available currencies.
     */
    public static Map<Currency, Double> getRates() {
        if (useCase == null)
            useCase = new RateUseCaseImpl(new LocalRateDataSource(), new RemoteRateDataSource());
        return useCase.getAllRates();
    }

}
