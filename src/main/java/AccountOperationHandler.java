import java.math.BigDecimal;

public interface AccountOperationHandler {
    void transfer(long sourceId, long destinationId, BigDecimal value);
    long createNewAccount();
    void deleteAccount(long id);
}
