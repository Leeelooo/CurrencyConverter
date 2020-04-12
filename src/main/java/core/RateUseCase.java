package core;

import java.util.HashMap;
import java.util.Map;

import utils.Config;
import vo.Currency;

public interface RateUseCase {
    boolean containsCurrency(String currencyCode);
    double getRate(Currency currency);
    Map<Currency, Double> getAllRates();
    void updateRates();
    static RateUseCase get() {
        return new RateUseCaseImpl(new LocalRateDataSource(), new RemoteRateDataSource());
    }
}

class RateUseCaseImpl implements RateUseCase {
    private RateDataSource local;
    private RateDataSource remote;
    private Map<Currency, Double> rates;

    public RateUseCaseImpl(RateDataSource local, RateDataSource remote) {
        this.local = local;
        this.remote = remote;
        rates = new HashMap<>();
    }

    @Override
    public boolean containsCurrency(String currencyCode) {
        if (currencyCode.equals(Config.BASE_CURRENCY_CODE))
            return true;
        return rates.containsKey(new Currency(currencyCode));
    }

    @Override
    public double getRate(Currency currency) {
        if (currency.getCode().equals(Config.BASE_CURRENCY_CODE))
            return 1.d;
        if (rates.isEmpty() || !rates.containsKey(currency)) 
            updateRates();
        return rates.get(currency);
    }
    
    @Override
    public Map<Currency, Double> getAllRates() {
        if (rates.isEmpty())
            updateRates();
        return rates;
    }

    @Override
    public void updateRates() {
        if (local.isValid()) {
            rates.clear();
            rates.putAll(local.getRates());
        } else {
            rates.clear();
            rates.putAll(remote.getRates());
        }
    }

}