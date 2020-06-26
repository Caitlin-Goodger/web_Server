package nz.ac.vuw.swen301.a2.server;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatsPNGServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");
        ArrayList<JSONObject> jsonLogs = LogsServlet.jsonLogs;
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

        BufferedImage bufferedImage = new BufferedImage(500, 350, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        ArrayList<Color> listOfColours = new ArrayList<>();
        listOfColours.add(Color.red);
        listOfColours.add(Color.orange);
        listOfColours.add(Color.yellow);
        listOfColours.add(Color.green);
        listOfColours.add(Color.blue);
        listOfColours.add(Color.pink);
        listOfColours.add(new Color(153, 0, 255));
        listOfColours.add(Color.white);

        int yValue = 40;
        int count  = 0;
        for(Map.Entry<String,Integer> entry : warningLevels.entrySet()) {
            graphics.setColor(listOfColours.get(count));
            graphics.drawString(entry.getKey(),30,yValue);
            graphics.drawRect(100, yValue-20,50*entry.getValue(),30);
            yValue = yValue + 40;
            count++;
        }
        graphics.dispose();

        OutputStream output= resp.getOutputStream();
        ImageIO.write(bufferedImage, "png", output);
//        FileInputStream in = new FileInputStream(file);
//        try {
//            byte[] buf = new byte[1024];
//            int counter = 0;
//            while ((counter = in.read(buf)) >= 0) {
//                output.write(buf, 0, counter);
//            }
//        } finally {
//            in.close();
//        }

        output.close();
        resp.setStatus(200);
    }

}
