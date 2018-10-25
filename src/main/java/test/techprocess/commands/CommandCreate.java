package test.techprocess.commands;

import test.messages.OperationResponse;
import test.messages.OperationStatus;
import test.techprocess.AccountOperationHandler;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static test.messages.Messages.INVALID_PARAMETER;

public class CommandCreate extends Command {

    @Override
    public OperationResponse execute(AccountOperationHandler accountOperationHandler, HttpServletRequest req) {
        String value = req.getParameter(VALUE);
        BigDecimal formatValue = getBigDecimalFormat(value);
        if (formatValue.compareTo(new BigDecimal(0)) <= 0) {
            return new OperationResponse(OperationStatus.ERROR, INVALID_PARAMETER + " " + VALUE + " : " + value);
        }
        return accountOperationHandler.createNewAccount(formatValue);
    }

}
