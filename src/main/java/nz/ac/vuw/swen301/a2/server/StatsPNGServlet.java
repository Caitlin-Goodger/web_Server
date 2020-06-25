package nz.ac.vuw.swen301.a2.server;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatsPNGServlet extends HttpServlet {

    public static ArrayList<JSONObject> jsonLogs = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("image/png");
        jsonLogs = testJSONLogs();
        System.out.print(jsonLogs.size());
        //jsonLogs = LogsServlet.jsonLogs;
        ArrayList<String> dates = getDates();
        ArrayList<String> warnings = new ArrayList<>();
        warnings.add("ALL");
        warnings.add("TRACE");
        warnings.add("DEBUG");
        warnings.add("INFO");
        warnings.add("WARN");
        warnings.add("ERROR");
        warnings.add("FATAL");
        warnings.add("OFF");
        Map<String,Integer> warningLevels = new HashMap<>();

        for(int i = 0; i < warnings.size(); i++) {
            int count = 0;
            for(JSONObject jsonObject : jsonLogs) {
                String level = jsonObject.getString("level");
                if(level.equals(warnings.get(i))) {
                    count++;
                }
            }
            warningLevels.put(warnings.get(i),count);
        }

        BufferedImage bufferedImage = new BufferedImage(500, 250, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        ArrayList<Color> listOfColours = new ArrayList<>();
        listOfColours.add(Color.red);
        listOfColours.add(Color.orange);
        listOfColours.add(Color.yellow);
        listOfColours.add(Color.green);
        listOfColours.add(Color.blue);
        listOfColours.add(Color.pink);
        listOfColours.add(new Color(153, 0, 255));
        listOfColours.add(Color.black);

        int yValue = 0;
        int count  = 0;
        for(String warn : warningLevels.keySet()) {
            graphics.drawString(warn,0,yValue);

            graphics.setColor(listOfColours.get(count));
            graphics.drawRect(100, yValue,50*warningLevels.get(warn),25);
            yValue = yValue + 30;
            count++;
        }
        graphics.dispose();

        File file = new File("file.png");
        ImageIO.write(bufferedImage,"png", file);


        out.close();
        resp.setStatus(200);
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
