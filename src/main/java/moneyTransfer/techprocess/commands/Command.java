package moneyTransfer.techprocess.commands;

import moneyTransfer.messages.OperationResponse;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandler;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

public abstract class Command {

    public final static String SRC_ID = "srcId";
    public final static String ID = "id";
    public final static String DEST_ID = "destId";
    public final static String VALUE = "value";
    public final static String VIP = "vip";
    public final static String COMMAND = "command";

    protected AccountOperationHandler accountOperationHandler = null;

    public abstract OperationResponse execute(Map<String, AccountOperationHandler> operationHandlerContainer, HttpServletRequest req);

    protected Long getLongFormat(String value){
        try {
           return Long.valueOf(value);
        } catch (NumberFormatException e){
            return 0L;
        }
    }

    protected boolean getBoolean(String value){
        return Boolean.valueOf(value);
    }

    protected BigDecimal getBigDecimalFormat(String value){
        try {
            return BigDecimal.valueOf(Double.valueOf(value)).setScale(2);
        } catch (NumberFormatException e){
            return new BigDecimal(0);
        }
    }
}
