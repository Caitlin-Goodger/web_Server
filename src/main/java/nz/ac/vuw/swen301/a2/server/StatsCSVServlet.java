package nz.ac.vuw.swen301.a2.server;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class StatsCSVServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=\"file.csv\"");
        ArrayList<JSONObject> jsonLogs = LogsServlet.jsonLogs;
        ArrayList<String> dates = getDates(jsonLogs);
        ArrayList<String> loggers = getLoggers(jsonLogs);
        ArrayList<String> threads = getThreads(jsonLogs);
        ArrayList<String> warnings = new ArrayList<>();
        warnings.add("ALL");
        warnings.add("TRACE");
        warnings.add("DEBUG");
        warnings.add("INFO");
        warnings.add("WARN");
        warnings.add("ERROR");
        warnings.add("FATAL");
        warnings.add("OFF");

        for(int i = 0; i< dates.size();i++) {
            out.write("\t");
            out.write(dates.get(i));
        }
        out.write("\n");
        for ( int i = 0; i <loggers.size(); i++) {
            out.write(loggers.get(i));
            for(int j = 0; j< dates.size();j++) {
                int count = 0;
                out.write("\t");
                for(JSONObject jsonObject : jsonLogs) {
                    String timestamp = jsonObject.get("timestamp").toString().substring(0,10);
                    String log = jsonObject.getString("logger");
                    if(timestamp.equals(dates.get(j)) && log.equals(loggers.get(i))) {
                        count++;
                    }
                }
                out.write(Integer.toString(count));
            }
            out.write("\n");
        }

        for ( int i = 0; i <warnings.size(); i++) {
            out.write(warnings.get(i));
            for(int j = 0; j< dates.size();j++) {
                int count = 0;
                out.write("\t");
                for(JSONObject jsonObject : jsonLogs) {
                    String timestamp = jsonObject.get("timestamp").toString().substring(0,10);
                    String level = jsonObject.getString("level");
                    if(timestamp.equals(dates.get(j)) && level.equals(warnings.get(i))) {
                        count++;
                    }
                }
                out.write(Integer.toString(count));
            }
            out.write("\n");
        }

        for ( int i = 0; i <threads.size(); i++) {
            out.write(threads.get(i));
            for(int j = 0; j< dates.size();j++) {
                int count = 0;
                out.write("\t");
                for(JSONObject jsonObject : jsonLogs) {
                    String timestamp = jsonObject.get("timestamp").toString().substring(0,10);
                    String thread = jsonObject.getString("thread");
                    if(timestamp.equals(dates.get(j)) && thread.equals(threads.get(i))) {
                        count++;
                    }
                }
                out.write(Integer.toString(count));
            }
            out.write("\n");
        }
        resp.setStatus(200);
        out.close();
    }

    public ArrayList<String> getDates(ArrayList<JSONObject> jsonLogs) {
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

    public ArrayList<String> getLoggers(ArrayList<JSONObject> jsonLogs) {
        ArrayList<String> loggers = new ArrayList<String>();
        for(JSONObject jsonObject : jsonLogs) {
            String log = jsonObject.getString("logger");
            if(!loggers.contains(log)) {
                loggers.add(log);
            }
        }
        return loggers;
    }

    public ArrayList<String> getThreads(ArrayList<JSONObject> jsonLogs) {
        ArrayList<String> threads = new ArrayList<String>();
        for(JSONObject jsonObject : jsonLogs) {
            String thread = jsonObject.getString("thread");
            if(!threads.contains(thread)) {
                threads.add(thread);
            }
        }
        return threads;
    }
}
