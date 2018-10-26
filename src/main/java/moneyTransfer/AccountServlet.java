package moneyTransfer;

import com.google.gson.Gson;
import moneyTransfer.messages.OperationResponse;

import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandler;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandlerVIPImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import moneyTransfer.techprocess.processPerformers.AccountOperationHandlerStandardImpl;
import moneyTransfer.techprocess.CommandsContainer;
import moneyTransfer.techprocess.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static moneyTransfer.messages.Messages.EMPTY_COMMAND;
import static moneyTransfer.messages.Messages.UNKNOWN_COMMAND;

public class AccountServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServlet.class);

    private final Map<String, AccountOperationHandler> operationHandlerContainer = new HashMap<>();
    private AccountOperationHandlerStandardImpl accountOperationHandler;
    private CommandsContainer commandsContainer;
    private Gson gson;

    private final static String COMMAND = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LOG.info("GET: {} {}",req.getRequestURL().toString(), req.getQueryString());
        OperationResponse result = execute(req.getParameter(COMMAND),req);
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
        this.operationHandlerContainer.put(AccountOperationHandlerStandardImpl.class.getSimpleName(), new AccountOperationHandlerStandardImpl());
        this.operationHandlerContainer.put(AccountOperationHandlerVIPImpl.class.getSimpleName(), new AccountOperationHandlerVIPImpl());
        commandsContainer = new CommandsContainer();
        commandsContainer.init();
        gson = new Gson();
    }
}
