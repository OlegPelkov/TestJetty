

import techprocess.AccountOperationHandlerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class MyServlet extends HttpServlet {

    private AccountOperationHandlerImpl accountOperationHandler;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        String sourceId = req.getParameter("source_id");
        String accountId = req.getParameter("id");
        String destanationId = req.getParameter("destination_id");
        String value = req.getParameter("value");
        Long actualSourceId = null;
        Long actualDestanationId  = null;
        Long actualAccountId  = null;
        String result = null;
        BigDecimal actualValue = BigDecimal.valueOf(Double.valueOf(value)).setScale(2);
        if(sourceId!=null) {
            actualSourceId = Long.valueOf(sourceId);
        }
        if(destanationId!=null) {
            actualDestanationId = Long.valueOf(destanationId);
        }
        if(accountId!=null) {
            actualAccountId = Long.valueOf(accountId);
        }
        if(command.equals("create")){
            result = accountOperationHandler.createNewAccount(actualValue);
        }
        if(command.equals("delete")){
            result = accountOperationHandler.deleteAccount(actualAccountId);
        }
        if(command.equals("transfer")){
            result = accountOperationHandler.transfer(actualSourceId, actualDestanationId, actualValue);
        }
        resp.getWriter().write(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void destroy() {
        System.out.println("-------- destroy  "+this);
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        System.out.println("------------ init  "+this);
        super.init();
        accountOperationHandler = new AccountOperationHandlerImpl();
    }
}
