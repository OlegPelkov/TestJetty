package moneyTransfer.data;

import moneyTransfer.account.Account;

public interface AccountDataHolder {
    Account get(Long key);
    Account put(Long key, Account value);
    boolean remove(Long key, Account value);
}
