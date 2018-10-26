package moneyTransfer.account;

import java.math.BigDecimal;

public interface Account {

    long getId();

    /**
     * Return 1 if success, 0 if not enough money, -1 if account is deleted
     * */
    int introduction(BigDecimal value);

    /**
     * Return 1 if success, 0 if not enough money, -1 if account is deleted
     * */
    int withdrawal(BigDecimal value);

    BigDecimal getCurrentValue();

    void lock();

    void unlock();

    boolean isDeleted();

    void setDeleted(boolean deletedStatus);

    boolean isVIP();
}

