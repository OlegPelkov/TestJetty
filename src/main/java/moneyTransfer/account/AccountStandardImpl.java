package moneyTransfer.account;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountStandardImpl implements Account {

    /**
     * Use concurrent lock
     **/

    private final Long id;
    private BigDecimal currentValue;
    private final Lock lock = new ReentrantLock();
    private final AtomicBoolean deletedStatus = new AtomicBoolean(false);

    public AccountStandardImpl(Long id, BigDecimal currentValue) {
        this.id = id;
        this.currentValue = currentValue;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    /**
     * result will be ignored in AccountOperationHandlerStandardImpl
     **/
    public int introduction(BigDecimal amount) {
        currentValue = currentValue.add(amount);
        return 1;
    }

    /**
     * return always 1
     **/
    public int withdrawal(BigDecimal amount) {
        currentValue = currentValue.subtract(amount);
        return 1;
    }

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public void unlock() {
        lock.unlock();
    }

    public boolean isDeleted() {
        return deletedStatus.get();
    }

    public void setDeleted(boolean deletedStatus) {
        this.deletedStatus.set(deletedStatus);
    }

    @Override
    public boolean isVIP() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountStandardImpl account = (AccountStandardImpl) o;

        return id.equals(account.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
