package nz.ac.vuw.swen301.a2.server;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class StatsXLSServlet extends HttpServlet {

    public static ArrayList<JSONObject> jsonLogs = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    public ArrayList<String> getDates() {
        ArrayList<String> dates = new ArrayList<String>();
        for(JSONObject jsonObject : jsonLogs) {
            String timestamp = jsonObject.get("timestamp").toString().substring(0,10);
            boolean found = false;
            if(!dates.contains(timestamp)) {
                dates.add(timestamp);
            }
        }
        return dates;
    }

    public ArrayList<String> getLoggers() {
        ArrayList<String> loggers = new ArrayList<String>();
        for(JSONObject jsonObject : jsonLogs) {
            String log = jsonObject.getString("logger");
            if(!loggers.contains(log)) {
                loggers.add(log);
            }
        }
        return loggers;
    }

    public ArrayList<String> getThreads() {
        ArrayList<String> threads = new ArrayList<String>();
        for(JSONObject jsonObject : jsonLogs) {
            String thread = jsonObject.getString("thread");
            if(!threads.contains(thread)) {
                threads.add(thread);
            }
        }
        return threads;
    }

    public ArrayList<JSONObject> testJSONLogs() {
        ArrayList<JSONObject> j = new ArrayList<>();

        JSONObject j1 = new JSONObject();
        j1.put("id", UUID.randomUUID().toString());
        j1.put("level", "ALL");
        j1.put("timestamp", "2019-07-29T09:12:33.001Z");
        j1.put("thread","main");
        j1.put("message", "help");
        j1.put("logger", "logger");

        j.add(j1);

        JSONObject j2 = new JSONObject();
        j2.put("id", UUID.randomUUID().toString());
        j2.put("level", "DEBUG");
        j2.put("timestamp", "2018-07-29T09:12:33.001Z");
        j2.put("thread","main");
        j2.put("message", "help");
        j2.put("logger", "l");

        j.add(j2);

        return j;
    }
}
