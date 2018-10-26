package moneyTransfer;

import com.google.gson.Gson;
import moneyTransfer.messages.OperationResponse;

import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandler;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandlerLockImpl;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandlerCASImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import moneyTransfer.techprocess.CommandsContainer;
import moneyTransfer.techprocess.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static moneyTransfer.messages.Messages.EMPTY_COMMAND;
import static moneyTransfer.messages.Messages.UNKNOWN_COMMAND;

public class AccountServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServlet.class);

    private  AccountOperationHandler operationHandlerContainer;
    private CommandsContainer commandsContainer;
    private Gson gson;
    private boolean cas = false;
    private long requestCounter = 0L;
    public AccountServlet(boolean cas) {
        this.cas = cas;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        requestCounter++;
        LOG.info("GET: -{}- {} {}",requestCounter,req.getRequestURL().toString(), req.getQueryString());
        OperationResponse result = execute(req.getParameter(Command.COMMAND),req);
        LOG.info("RESULT: {}",result.toString());
        resp.getWriter().write(gson.toJson(result));
    }

    private OperationResponse execute(String command, HttpServletRequest req){
        if(command==null){
            return new OperationResponse(OperationStatus.ERROR,EMPTY_COMMAND);
        }
        Command commandHandler = commandsContainer.getCommand(command);
        if(commandHandler!=null){
            return commandHandler.execute(operationHandlerContainer, req);
        }
        return new OperationResponse(OperationStatus.ERROR,UNKNOWN_COMMAND + " : "+command);
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
        if(cas){
            operationHandlerContainer = new AccountOperationHandlerCASImpl();
        } else {
            operationHandlerContainer = new AccountOperationHandlerLockImpl();
        }
        commandsContainer = new CommandsContainer();
        commandsContainer.init();
        gson = new Gson();
    }
}
