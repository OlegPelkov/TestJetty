package account;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountImpl implements Account {

    private final Long id;
    private BigDecimal currentValue;
    private final Lock lock = new ReentrantLock();
    private final AtomicBoolean deletedStatus = new AtomicBoolean(false);

    public AccountImpl(Long id, BigDecimal currentValue) {
        this.id = id;
        this.currentValue = currentValue;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getCurrentValue(){
        return currentValue;
    }

    public void introduction(BigDecimal amount) {
         currentValue.add(amount);
    }

    public void widthrawal(BigDecimal amount) {
        currentValue = currentValue.subtract(amount);
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
}
