import java.math.BigDecimal;

public class AccountOperationHandlerImpl implements AccountOperationHandler {

    AccountDataHolder accountDataHolder = AccountDataHolder.getInstance();

    @Override
    public void transfer(long sourceId, long destinationId, BigDecimal value) {
        Account srcAccount = accountDataHolder.get(sourceId);
        Account destAccount = accountDataHolder.get(sourceId);
        if(destAccount!=null && srcAccount!=null) {
            synchronized (this) {

            }
        }
    }

    @Override
    public long createNewAccount() {
        return 0;
    }

    @Override
    public void deleteAccount(long id) {

    }
}
