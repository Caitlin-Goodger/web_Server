package nz.ac.vuw.swen301.a2.server;

import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.ArrayList;

import org.apache.log4j.spi.LoggingEvent;


public class LogsServlet extends HttpServlet {

    public LogsServlet() {
    }

    public static ArrayList<JSONObject> jsonLogs = new ArrayList<>();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        String sLimit = req.getParameter("limit");
        String sLevel = req.getParameter("level");
        int limit = 0;
        try {
            limit = Integer.parseInt(sLimit);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid limit");
            return;
        }

        if (sLimit == null || limit < 0 || limit > Integer.MAX_VALUE) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid limit");
            return;
        }
        if (sLevel == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid level");
            return;
        }
        ArrayList<LoggingEvent> logsEvents = new ArrayList<>();
        int count = 0;
        int levelInt = getLevel(sLevel);
        for(JSONObject jsonObj : jsonLogs) {
            if(count<limit && levelInt <= getLevel(jsonObj.get("level").toString())) {
                count++;
                out.println(jsonObj);
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid json");
            return;
        }
        JSONObject jsonObject = new JSONObject(jb.toString());

        boolean foundDupicate = false;
        for(JSONObject j : jsonLogs) {
            if(j.get("id").equals(jsonObject.get("id"))) {
                foundDupicate = true;
            }
        }
        if(!foundDupicate) {
            jsonLogs.add(0, jsonObject);
            resp.sendError(HttpServletResponse.SC_CREATED, "Log added");
            return;
        } else {
            resp.sendError(HttpServletResponse.SC_CONFLICT, "Duplicate log found");
            return;
        }
    }

    public int getLevel(String level) {
        if(level.equals("ALL")) {
            return 1;
        } else if(level.equals("TRACE")) {
            return 2;
        } else if (level.equals("DEBUG")) {
            return 3;
        } else if(level.equals("INFO")) {
            return 4;
        } else if (level.equals("WARN")) {
            return 5;
        } else if (level.equals("ERROR")) {
            return 6;
        } else if (level.equals("FATAL")) {
            return 7;
        } else if(level.equals("OFF")) {
            return 8;
        } else {
            return -1;
        }
    }
}