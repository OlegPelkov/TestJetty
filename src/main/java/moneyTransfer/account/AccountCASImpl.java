package moneyTransfer.account;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AccountCASImpl implements Account {

    /**
     * Use CAS algorithm for introduction and withdrawal
     **/
    private final Long id;
    private AtomicReference<BigDecimal> currentValue = new AtomicReference<>();
    private final AtomicBoolean deletedStatus = new AtomicBoolean(false);

    public AccountCASImpl(Long id, BigDecimal currentValue) {
        this.id = id;
        this.currentValue.set(currentValue);
    }

    public long getId() {
        return id;
    }

    public BigDecimal getCurrentValue() {
        return currentValue.get();
    }

    public int introduction(BigDecimal amount) {
        for (;;) {
            BigDecimal oldValue = currentValue.get();
            if (currentValue.compareAndSet(oldValue, oldValue.add(amount))) {
                if (isDeleted()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }

    public int withdrawal(BigDecimal amount) {
        for (;;) {
            BigDecimal oldValue = currentValue.get();
            if (oldValue.compareTo(amount) < 0) {
                return 0;
            }
            if (currentValue.compareAndSet(oldValue, oldValue.subtract(amount)))
                if (isDeleted()) {
                    return -1;
                } else {
                    return 1;
                }
        }
    }

    @Override
    public void lock() {
    }

    @Override
    public void unlock() {

    }

    public boolean isDeleted() {
        return deletedStatus.get();
    }

    public void setDeleted(boolean deletedStatus) {
        this.deletedStatus.set(deletedStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountCASImpl account = (AccountCASImpl) o;

        return id.equals(account.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
