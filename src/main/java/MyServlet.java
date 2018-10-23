

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        String sourceId = req.getParameter("source_id");
        String destanationId = req.getParameter("destination_id");
        String value = req.getParameter("value");
        String result = this + " Get " + command + " from " + sourceId + " to " + destanationId + " value : " +value;
        System.out.println(result);
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
    }
}
