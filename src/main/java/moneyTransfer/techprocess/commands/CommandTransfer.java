package moneyTransfer.techprocess.commands;

import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandler;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandlerStandardImpl;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandlerVIPImpl;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Map;

import static moneyTransfer.messages.Messages.ACCOUNT_NOT_SUPPORTED;
import static moneyTransfer.messages.Messages.INVALID_PARAMETER;

public class CommandTransfer extends Command {

    public final static String NAME = "transfer";

    @Override
    public OperationResponse execute(Map<String, AccountOperationHandler> operationHandlerContainer, HttpServletRequest req) {
        String srcId = req.getParameter(SRC_ID);
        String destId = req.getParameter(DEST_ID);
        String value = req.getParameter(VALUE);
        String vip = req.getParameter(VIP);
        BigDecimal formatValue = getBigDecimalFormat(value);
        if (formatValue.compareTo(new BigDecimal(0)) <= 0) {
            return new OperationResponse(OperationStatus.ERROR, INVALID_PARAMETER + " " + VALUE + " : " + value);
        }
        Long formatSrcId = getLongFormat(srcId);
        if (formatSrcId <= 0L) {
            return new OperationResponse(OperationStatus.ERROR, INVALID_PARAMETER + " " + SRC_ID + " : " + srcId);
        }
        Long formatDestId = getLongFormat(destId);
        if (formatDestId <= 0L) {
            return new OperationResponse(OperationStatus.ERROR, INVALID_PARAMETER + " " + DEST_ID + " : " + destId);
        }
        if (!getBoolean(vip)) {
            accountOperationHandler = operationHandlerContainer.get(AccountOperationHandlerStandardImpl.class.getSimpleName());
        } else {
            accountOperationHandler = operationHandlerContainer.get(AccountOperationHandlerVIPImpl.class.getSimpleName());
        }
        if (accountOperationHandler != null) {
            return accountOperationHandler.transfer(formatSrcId, formatDestId, formatValue);
        } else {
            return new OperationResponse(OperationStatus.ERROR, ACCOUNT_NOT_SUPPORTED + "VIP=" + getBoolean(vip));
        }
    }

}
