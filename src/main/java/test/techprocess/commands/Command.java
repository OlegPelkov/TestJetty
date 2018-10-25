package test.techprocess.commands;

import test.messages.OperationResponse;
import test.techprocess.AccountOperationHandler;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public abstract class Command {

    protected final static String SRC_ID = "srcId";
    protected final static String ID = "id";
    protected final static String DEST_ID = "destId";
    protected final static String VALUE = "value";

    public abstract OperationResponse execute(AccountOperationHandler accountOperationHandler, HttpServletRequest req);

    protected Long getLongFormat(String value){
        try {
           return Long.valueOf(value);
        } catch (NumberFormatException e){
            return 0L;
        }
    }

    protected BigDecimal getBigDecimalFormat(String value){
        try {
            return BigDecimal.valueOf(Double.valueOf(value)).setScale(2);
        } catch (NumberFormatException e){
            return new BigDecimal(0);
        }
    }
}
