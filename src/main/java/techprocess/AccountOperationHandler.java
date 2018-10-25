package techprocess;

import messages.OperationResponce;

import java.math.BigDecimal;

public interface AccountOperationHandler {
    OperationResponce transfer(long sourceId, long destinationId, BigDecimal value);
    OperationResponce createNewAccount(BigDecimal initialValue);
    OperationResponce deleteAccount(long id);
}
