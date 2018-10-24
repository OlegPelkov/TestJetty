package data;

import account.Account;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountDataHolderImpl implements AccountDataHolder {

    public static class SingletonHolder {
        public static final AccountDataHolderImpl INSTANCE = new AccountDataHolderImpl();
    }

    public static AccountDataHolderImpl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Map<Long, Account> accountData = new ConcurrentHashMap<>();

    public Account get(Long key) {
        return accountData.get(key);
    }

    public Account put(Long key, Account value) {
        return accountData.put(key, value);
    }

    public boolean remove(Long key, Account value) {
        return accountData.remove(key, value);
    }
}
