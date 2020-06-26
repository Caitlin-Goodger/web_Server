package nz.ac.vuw.swen301.a2.server;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.codehaus.plexus.util.FileUtils;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class StatsXLSServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //PrintWriter out = resp.getWriter();
        resp.setContentType("application/vnd.ms-excel");

        //jsonLogs = testJSONLogs();
        //System.out.print(jsonLogs.size());
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

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Stats");

        int rows = 0;
        Row row = sheet.createRow(rows);
        for(int i = 0; i< dates.size();i++) {
            Cell cell = row.createCell((i+1));
            cell.setCellValue(dates.get(i));
        }
        rows++;
        for ( int i = 0; i <loggers.size(); i++) {
            row = sheet.createRow(rows);
            Cell cell = row.createCell(0);
            cell.setCellValue(loggers.get(i));
            for(int j = 0; j< dates.size();j++) {
                int count = 0;
                for(JSONObject jsonObject : jsonLogs) {
                    String timestamp = jsonObject.get("timestamp").toString().substring(0,10);
                    String log = jsonObject.getString("logger");
                    if(timestamp.equals(dates.get(j)) && log.equals(loggers.get(i))) {
                        count++;
                    }
                }
                Cell cell2 = row.createCell(j+1);
                cell2.setCellValue(count);
            }
           rows++;
        }

        for ( int i = 0; i <warnings.size(); i++) {
            row = sheet.createRow(rows);
            Cell cell = row.createCell(0);
            cell.setCellValue(warnings.get(i));
            for(int j = 0; j< dates.size();j++) {
                int count = 0;
                for(JSONObject jsonObject : jsonLogs) {
                    String timestamp = jsonObject.get("timestamp").toString().substring(0,10);
                    String level = jsonObject.getString("level");
                    if(timestamp.equals(dates.get(j)) && level.equals(warnings.get(i))) {
                        count++;
                    }
                }
                Cell cell2 = row.createCell(j+1);
                cell2.setCellValue(count);
            }
            rows++;
        }

        for ( int i = 0; i <threads.size(); i++) {
            row = sheet.createRow(rows);
            Cell cell = row.createCell(0);
            cell.setCellValue(threads.get(i));
            for(int j = 0; j< dates.size();j++) {
                int count = 0;
                for(JSONObject jsonObject : jsonLogs) {
                    String timestamp = jsonObject.get("timestamp").toString().substring(0,10);
                    String thread = jsonObject.getString("thread");
                    if(timestamp.equals(dates.get(j)) && thread.equals(threads.get(i))) {
                        count++;
                    }
                }
                Cell cell2 = row.createCell(j+1);
                cell2.setCellValue(count);
            }
            rows++;
        }

        OutputStream output= resp.getOutputStream();

        output.write(workbook.getBytes());
        output.close();
        FileOutputStream fileOut = new FileOutputStream("file.xls");
        workbook.write(fileOut);
        fileOut.close();
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
