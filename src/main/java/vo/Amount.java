package vo;

import java.util.Objects;

/**
 * POJO for representation of an amount of money
 * @see Currency
 */
public class Amount {
    private final Currency currency;
    private final double amount;

    public Amount(String currencyCode, double amount) {
        this(new Currency(currencyCode), amount);
    }

    public Amount(Currency currency, double amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Amount other = (Amount) obj;
        return Double.compare(other.amount, amount) == 0 &&
                currency.equals(other.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}