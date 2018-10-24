package techprocess;

import java.math.BigDecimal;

public interface AccountOperationHandler {
    String transfer(long sourceId, long destinationId, BigDecimal value);
    String createNewAccount(BigDecimal initialValue);
    String deleteAccount(long id);
}
