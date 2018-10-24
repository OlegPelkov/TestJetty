package account;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountImpl implements Account {

    private Long id;
    private AtomicReference<BigDecimal> currentValue;
    private Lock lock = new ReentrantLock();
    private AtomicBoolean deletedStatus = new AtomicBoolean(false);

    public AccountImpl(Long id, BigDecimal currentValue) {
        this.id = id;
        this.currentValue = new AtomicReference<>(currentValue);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getCurrentValue(){
        return currentValue.get();
    }

    public void introduction(BigDecimal amount) {
        while (true) {
            BigDecimal oldVal = currentValue.get();
            if (currentValue.compareAndSet(oldVal, oldVal.add(amount)))
                return;
        }
    }

    public void widthrawal(BigDecimal amount) {
        currentValue.set(currentValue.get().subtract(amount));
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
