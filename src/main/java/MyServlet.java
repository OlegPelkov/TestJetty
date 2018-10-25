

import com.google.gson.Gson;
import messages.OperationResponce;

import messages.OperationStatus;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import techprocess.AccountOperationHandlerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static messages.Messages.EMPTY_COMMAND;
import static messages.Messages.INVALID_PARAMETER;
import static messages.Messages.UNKNOWN_COMMAND;

public class MyServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MyServlet.class);

    private AccountOperationHandlerImpl accountOperationHandler;
    private Gson gson;

    private final static String COMMAND = "command";
    private final static String SRC_ID = "src_id";
    private final static String ID = "id";
    private final static String DEST_ID = "dest_id";
    private final static String ACCOUNT_ID = "account_id";
    private final static String VALUE = "value";

    private final static String COMMAND_TRANCFER = "transfer";
    private final static String COMMAND_CREATE = "create";
    private final static String COMMAND_DELETE = "delete";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        LOG.info("GET: {} ",req.getRequestURL().toString());

        String command = req.getParameter(COMMAND);
        String srcId = req.getParameter(SRC_ID);
        String accountId = req.getParameter(ID);
        String destId = req.getParameter(DEST_ID);
        String value = req.getParameter(VALUE);
        OperationResponce result = execute(command,srcId,accountId,destId,value);
        LOG.info("RESULT: {}",result.toString());
        resp.getWriter().write(gson.toJson(result));
    }

    private OperationResponce execute(String command, String srcId, String accountId, String destId , String value){

        Long formatSrcId = null;
        Long formatDestId  = null;
        Long formatAccountId  = null;

        if(command==null){
            return new OperationResponce(OperationStatus.ERROR,EMPTY_COMMAND);
        }

        if (command.equals(COMMAND_TRANCFER)) {
            if (value != null && !NumberUtils.isParsable(value)) {
                return new OperationResponce(OperationStatus.ERROR, INVALID_PARAMETER + " " + VALUE + " : " + value);
            }
            if (srcId != null && !NumberUtils.isParsable(srcId)) {
                return new OperationResponce(OperationStatus.ERROR, INVALID_PARAMETER + " " + SRC_ID + " : " + srcId);
            }
            if (destId != null && !NumberUtils.isParsable(destId)) {
                return new OperationResponce(OperationStatus.ERROR, INVALID_PARAMETER + " " + DEST_ID + " : " + destId);
            }
            formatSrcId = Long.valueOf(srcId);
            formatDestId = Long.valueOf(destId);
            return accountOperationHandler.transfer(formatSrcId, formatDestId, formatValue(value));
        }

        if(command.equals(COMMAND_CREATE)){
            if (value != null && !NumberUtils.isParsable(value)) {
                return new OperationResponce(OperationStatus.ERROR, INVALID_PARAMETER + " " + VALUE + " : " + value);
            }
            return accountOperationHandler.createNewAccount(formatValue(value));
        }
        if(command.equals(COMMAND_DELETE)){
            if (accountId != null && !NumberUtils.isParsable(accountId)) {
                return new OperationResponce(OperationStatus.ERROR, INVALID_PARAMETER + " " + ACCOUNT_ID + " : " + accountId);
            }
            formatAccountId = Long.valueOf(accountId);
            return accountOperationHandler.deleteAccount(formatAccountId);
        }

        return new OperationResponce(OperationStatus.ERROR,UNKNOWN_COMMAND);

    }

    private BigDecimal formatValue(String value){
        return BigDecimal.valueOf(Double.valueOf(value)).setScale(2);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        accountOperationHandler = new AccountOperationHandlerImpl();
        gson = new Gson();
    }
}
