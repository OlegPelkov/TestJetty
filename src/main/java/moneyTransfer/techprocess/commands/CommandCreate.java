package moneyTransfer.techprocess.commands;

import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandler;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandlerStandardImpl;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

import static moneyTransfer.messages.Messages.ACCOUNT_NOT_SUPPORTED;
import static moneyTransfer.messages.Messages.INVALID_PARAMETER;

public class CommandCreate extends Command {

    @Override
    public OperationResponse execute(Map<String, AccountOperationHandler> operationHandlerContainer, HttpServletRequest req) {
        String value = req.getParameter(VALUE);
        String vip = req.getParameter(VIP);
        BigDecimal formatValue = getBigDecimalFormat(value);
        if (formatValue.compareTo(new BigDecimal(0)) <= 0) {
            return new OperationResponse(OperationStatus.ERROR, INVALID_PARAMETER + " " + VALUE + " : " + value);
        }
        accountOperationHandler = operationHandlerContainer.get(AccountOperationHandlerStandardImpl.class.getSimpleName());
        if (accountOperationHandler != null) {
            return accountOperationHandler.createNewAccount(formatValue, getBoolean(vip));
        } else {
            return new OperationResponse(OperationStatus.ERROR, ACCOUNT_NOT_SUPPORTED + "VIP=" + getBoolean(vip));
        }

    }

}
