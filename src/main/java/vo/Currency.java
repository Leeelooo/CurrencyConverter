package vo;

import java.util.Objects;

/**
 * POJO for currency code
 */
public class Currency {
    private final String code;

    public Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Currency another = (Currency) obj;
        return Objects.equals(code, another.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}