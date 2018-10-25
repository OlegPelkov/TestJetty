package test;

import com.google.gson.Gson;
import test.messages.OperationResponse;

import test.messages.OperationStatus;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import test.techprocess.AccountOperationHandlerImpl;
import test.techprocess.CommandsContainer;
import test.techprocess.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

import static test.messages.Messages.EMPTY_COMMAND;
import static test.messages.Messages.INVALID_PARAMETER;
import static test.messages.Messages.UNKNOWN_COMMAND;

public class AccountServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServlet.class);

    private AccountOperationHandlerImpl accountOperationHandler;
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
            return commandHandler.execute(accountOperationHandler, req);
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
        accountOperationHandler = new AccountOperationHandlerImpl();
        commandsContainer = new CommandsContainer();
        commandsContainer.init();
        gson = new Gson();
    }
}
