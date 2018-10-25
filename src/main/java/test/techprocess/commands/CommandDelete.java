package test.techprocess.commands;

import test.messages.OperationResponse;
import test.messages.OperationStatus;
import test.techprocess.AccountOperationHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

import static test.messages.Messages.INVALID_PARAMETER;

public class CommandDelete extends Command {

    @Override
    public OperationResponse execute(AccountOperationHandler accountOperationHandler, HttpServletRequest req) {
        String accountId = req.getParameter(ID);
        Long formatAccountId = getLongFormat(accountId);
        if (formatAccountId <=0L) {
            return new OperationResponse(OperationStatus.ERROR, INVALID_PARAMETER + " " + VALUE + " : " + accountId);
        }
        return accountOperationHandler.deleteAccount(formatAccountId);
    }

}
