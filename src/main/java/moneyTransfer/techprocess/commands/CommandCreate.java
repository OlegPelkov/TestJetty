package moneyTransfer.techprocess.commands;

import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandler;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static moneyTransfer.messages.Messages.INVALID_PARAMETER;

public class CommandCreate extends Command {

    public final static String NAME = "create";

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
