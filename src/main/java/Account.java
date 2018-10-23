import java.math.BigDecimal;

public interface Account {

    long getId();

    void setId(long id);

    void introduction(BigDecimal value);

    void widthrawal(BigDecimal value);

}

