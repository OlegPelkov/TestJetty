package test.techprocess.commands;

import test.messages.OperationResponse;
import test.messages.OperationStatus;
import test.techprocess.AccountOperationHandler;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;

import static test.messages.Messages.INVALID_PARAMETER;

public class CommandTransfer extends Command {

    @Override
    public OperationResponse execute(AccountOperationHandler accountOperationHandler, HttpServletRequest req) {
        String srcId = req.getParameter(SRC_ID);
        String destId = req.getParameter(DEST_ID);
        String value = req.getParameter(VALUE);
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
        return accountOperationHandler.transfer(formatSrcId, formatDestId, formatValue);
    }

}
