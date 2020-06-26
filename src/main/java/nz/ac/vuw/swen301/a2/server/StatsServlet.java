package nz.ac.vuw.swen301.a2.server;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

public class StatsServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        ArrayList<JSONObject> jsonLogs = LogsServlet.jsonLogs;
        jsonLogs = testJSONLogs();
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

        out.write("<html>");
        out.write("<body>");
        out.write("<table border ='1' id = 'table1' >");
        out.write("<tr>");
        out.write("<td> </td>");
        for(int i = 0; i< dates.size();i++) {
            out.write("<td>");
            out.write(dates.get(i));
            out.write("</td>");
        }
        out.write("</tr>");

        for ( int i = 0; i <loggers.size(); i++) {
            out.write("<tr>");
            out.write("<td>");
            out.write(loggers.get(i));
            out.write("</td>");
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
                out.write("<td>");
                out.write(Integer.toString(count));
                out.write("</td>");
            }
            out.write("</tr>");
        }


        for ( int i = 0; i <warnings.size(); i++) {
            out.write("<tr>");
            out.write("<td>");
            out.write(warnings.get(i));
            out.write("</td>");
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
                out.write("<td>");
                out.write(Integer.toString(count));
                out.write("</td>");
            }
            out.write("</tr>");
        }


        for ( int i = 0; i <threads.size(); i++) {
            out.write("<tr>");
            out.write("<td>");
            out.write(threads.get(i));
            out.write("</td>");
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
                out.write("<td>");
                out.write(Integer.toString(count));
                out.write("</td>");
            }
            out.write("</tr>");
        }
        out.write("</table>");
        out.write("</body>");
        out.write("</html>");
        out.close();
        resp.setStatus(200);
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
