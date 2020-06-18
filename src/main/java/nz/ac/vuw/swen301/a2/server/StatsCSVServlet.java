package nz.ac.vuw.swen301.a2.server;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class StatsCSVServlet extends HttpServlet {

    public static ArrayList<JSONObject> jsonLogs = new ArrayList<>();
    public static Set<Instant> dates = new HashSet<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("test/csv");
        jsonLogs = LogsServlet.jsonLogs;
        ArrayList<String> dates = getDates();
    }

    public ArrayList<String> getDates() {
        ArrayList<String> dates = new ArrayList<String>();
        for(JSONObject jsonObject : jsonLogs) {
            String timestamp = jsonObject.get("timestamp").toString().substring(0,10);
            boolean found = false;
            for(String date : dates) {
                if(date.equals(timestamp)) {
                    found = true;
                }
            }
            if(!found){
                dates.add(timestamp);
            }
        }
        return dates;
    }
}
