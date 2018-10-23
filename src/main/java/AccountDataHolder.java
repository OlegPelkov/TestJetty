import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountDataHolder {

    public static class SingletonHolder {
        public static final AccountDataHolder INSTANCE = new AccountDataHolder();
    }

    public static AccountDataHolder getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private Map<Long, Account> accountData = new ConcurrentHashMap<>();

    public Account get(Long key) {
        return accountData.get(key);
    }

    public Account put(Long key, Account value) {
        return accountData.put(key, value);
    }
}
