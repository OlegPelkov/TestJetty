import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class AccountImpl implements Account {

    private Long id;
    private AtomicReference<BigDecimal> currentValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void introduction(BigDecimal amount) {
        while (true) {
            BigDecimal oldVal = currentValue.get();
            if (currentValue.compareAndSet(oldVal, oldVal.add(amount)))
                return;
        }
    }

    public void widthrawal(BigDecimal amount) {
        while (true) {
            BigDecimal oldVal = currentValue.get();
            if (currentValue.compareAndSet(oldVal, oldVal.subtract(amount)))
                return;
        }
    }
}
