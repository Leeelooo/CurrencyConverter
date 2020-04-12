import core.CurrencyConverter;
import org.junit.Assert;
import org.junit.Test;

import vo.Amount;

import java.util.List;
import java.util.Random;

public class CurrencyTests {

    @Test
    public void Converts() {
        Random random = new Random();
        List<String> codes = CurrencyConverter.getAllCodes();
        for (int i = 0; i < 100; i++) {
            Amount amount = CurrencyConverter.convertTo(
                    codes.get(random.nextInt(codes.size())),
                    (double) random.nextInt(10_000),
                    codes.get(random.nextInt(codes.size()))
            );
            Assert.assertNotNull(amount);
        }
    }

    @Test
    public void Without_ToCode() {
        Amount amount = CurrencyConverter.convertTo(
                "USD",
                120.d,
                null
        );
        assert amount != null;
        Assert.assertEquals(amount.getCurrency().getCode(), "EUR");
    }

    @Test
    public void Without_FromCode() {
        Amount amount = CurrencyConverter.convertTo(
                null,
                120.d,
                "EUR"
        );
        assert amount != null;
        Assert.assertEquals(120.d, amount.getAmount(), 0.0);
    }

    @Test
    public void Without_Amount() {
        Amount amount = CurrencyConverter.convertTo(
                null,
                null,
                "USD"
        );
        Assert.assertNull(amount);
    }

}
