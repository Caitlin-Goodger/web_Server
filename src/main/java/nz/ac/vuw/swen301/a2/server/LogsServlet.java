package nz.ac.vuw.swen301.a2.server;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.log4j.spi.LoggingEvent;



public class LogsServlet extends HttpServlet {

    public static ArrayList<LoggingEvent> logs = new ArrayList<>();;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        String sLimit = req.getParameter("limit");
        String sLevel = req.getParameter("level");
        int limit = Integer.parseInt(sLimit);
        ArrayList<LoggingEvent> logsEvents = new ArrayList<>()
        int count = 0;
        for(LoggingEvent event : logs) {
            if(count<limit) {
                count++;
                logsEvents.add(event);
            }
        }
        String jsonArray = new Gson().toJson(logsEvents);
        out.println(jsonArray);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        String id = req.getParameter("id");
        out.println("<html>");
        out.println("<body>");
        out.println(id);
        out.println(id);
        out.println("</body>");
        out.println("</html>");
    }
}
