package test.account;

import java.math.BigDecimal;

public interface Account {

    long getId();

    void introduction(BigDecimal value);

    void widthrawal(BigDecimal value);

    BigDecimal getCurrentValue();

    void lock();

    void unlock();

    boolean isDeleted();

    void setDeleted(boolean deletedStatus);
}

