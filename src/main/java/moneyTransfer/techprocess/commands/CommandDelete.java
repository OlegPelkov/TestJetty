package moneyTransfer.techprocess.commands;

import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandler;

import javax.servlet.http.HttpServletRequest;

import static moneyTransfer.messages.Messages.INVALID_PARAMETER;

public class CommandDelete extends Command {

    public final static String NAME = "delete";

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
