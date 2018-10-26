package moneyTransfer.techprocess.commands;

import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandler;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandlerStandardImpl;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static moneyTransfer.messages.Messages.ACCOUNT_NOT_SUPPORTED;
import static moneyTransfer.messages.Messages.INVALID_PARAMETER;

public class CommandDelete extends Command {

    public final static String NAME = "delete";

    @Override
    public OperationResponse execute(Map<String, AccountOperationHandler> operationHandlerContainer, HttpServletRequest req) {
        String accountId = req.getParameter(ID);
        Long formatAccountId = getLongFormat(accountId);
        if (formatAccountId <=0L) {
            return new OperationResponse(OperationStatus.ERROR, INVALID_PARAMETER + " " + VALUE + " : " + accountId);
        }
        accountOperationHandler = operationHandlerContainer.get(AccountOperationHandlerStandardImpl.class.getSimpleName());
        if (accountOperationHandler != null) {
            return accountOperationHandler.deleteAccount(formatAccountId);
        } else {
            return new OperationResponse(OperationStatus.ERROR, ACCOUNT_NOT_SUPPORTED );
        }

    }

}
