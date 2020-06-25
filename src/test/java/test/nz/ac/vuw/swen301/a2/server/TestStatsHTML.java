package test.nz.ac.vuw.swen301.a2.server;

import nz.ac.vuw.swen301.a2.server.LogsServlet;
import nz.ac.vuw.swen301.a2.server.StatsCSVServlet;
import nz.ac.vuw.swen301.a2.server.StatsServlet;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TestStatsHTML {


    @Test
    public void testContentTypeCorrect() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","1234");
        jsonObject.put("message","Testing");
        jsonObject.put("thread", "Main");
        jsonObject.put("timestamp", "2019-07-29T09:12:33.001Z");
        jsonObject.put("logger", "com.example.foo");
        jsonObject.put("level", "DEBUG");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id","12345678bfxsfdsf");
        jsonObject2.put("message","Testing");
        jsonObject2.put("thread", "Main");
        jsonObject2.put("timestamp", "2019-07-29T09:12:33.001Z");
        jsonObject2.put("logger", "com.example.foo");
        jsonObject2.put("level", "DEBUG");

        req.setContentType("application/json");
        req.setContent(jsonObject.toString().getBytes("utf-8"));

        LogsServlet logsServlet = new LogsServlet();
        logsServlet.setJSONLogsToNothing();
        logsServlet.doPost(req,resp);


        MockHttpServletRequest req2 = new MockHttpServletRequest();
        MockHttpServletResponse resp2 = new MockHttpServletResponse();
        req2.setContent(jsonObject2.toString().getBytes("utf-8"));
        logsServlet.doPost(req2,resp2);

        MockHttpServletRequest req3 = new MockHttpServletRequest();
        MockHttpServletResponse resp3 = new MockHttpServletResponse();
        StatsServlet statsServlet = new StatsServlet();
        statsServlet.doGet(req3,resp3);

        assertEquals("text/html",resp3.getContentType());
    }

    @Test
    public void testValuesCanBeParsed() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","1234");
        jsonObject.put("message","Testing");
        jsonObject.put("thread", "Main");
        jsonObject.put("timestamp", "2019-07-29T09:12:33.001Z");
        jsonObject.put("logger", "com.example.foo");
        jsonObject.put("level", "DEBUG");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id","12345678bfxsfdsf");
        jsonObject2.put("message","Testing");
        jsonObject2.put("thread", "Main");
        jsonObject2.put("timestamp", "2019-09-29T09:12:33.001Z");
        jsonObject2.put("logger", "com.example.foo");
        jsonObject2.put("level", "DEBUG");

        req.setContentType("application/json");
        req.setContent(jsonObject.toString().getBytes("utf-8"));

        LogsServlet logsServlet = new LogsServlet();
        logsServlet.setJSONLogsToNothing();
        logsServlet.doPost(req,resp);


        MockHttpServletRequest req2 = new MockHttpServletRequest();
        MockHttpServletResponse resp2 = new MockHttpServletResponse();
        req2.setContent(jsonObject2.toString().getBytes("utf-8"));
        logsServlet.doPost(req2,resp2);

        MockHttpServletRequest req3 = new MockHttpServletRequest();
        MockHttpServletResponse resp3 = new MockHttpServletResponse();
        StatsServlet statsServlet = new StatsServlet();
        statsServlet.doGet(req3,resp3);

        Document doc = Jsoup.parse(resp3.getContentAsString());
        assertTrue(doc.toString() != null);
    }

    @Test
    public void testValuesAreColumnsNumbersAreCorrect() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","1234");
        jsonObject.put("message","Testing");
        jsonObject.put("thread", "Main");
        jsonObject.put("timestamp", "2019-07-29T09:12:33.001Z");
        jsonObject.put("logger", "com.example.foo");
        jsonObject.put("level", "DEBUG");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id","12345678bfxsfdsf");
        jsonObject2.put("message","Testing");
        jsonObject2.put("thread", "Main");
        jsonObject2.put("timestamp", "2019-09-29T09:12:33.001Z");
        jsonObject2.put("logger", "com.example.foo");
        jsonObject2.put("level", "DEBUG");

        req.setContentType("application/json");
        req.setContent(jsonObject.toString().getBytes("utf-8"));

        LogsServlet logsServlet = new LogsServlet();
        logsServlet.setJSONLogsToNothing();
        logsServlet.doPost(req,resp);


        MockHttpServletRequest req2 = new MockHttpServletRequest();
        MockHttpServletResponse resp2 = new MockHttpServletResponse();
        req2.setContent(jsonObject2.toString().getBytes("utf-8"));
        logsServlet.doPost(req2,resp2);

        MockHttpServletRequest req3 = new MockHttpServletRequest();
        MockHttpServletResponse resp3 = new MockHttpServletResponse();
        StatsServlet statsServlet = new StatsServlet();
        statsServlet.doGet(req3,resp3);

        // Assert that each row has three columns in it.
        Document doc = Jsoup.parse(resp3.getContentAsString());
        for (Element table : doc.select("table")) {
            for (Element row : table.select("tr")) {
                Elements tds = row.select("td");
                assertTrue((tds.get(0).text() != null));
                assertTrue((tds.get(1).text() != null));
                assertTrue((tds.get(2).text() != null));
            }
        }
    }

    @Test
    public void testValuesAreCorrect() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","1234");
        jsonObject.put("message","Testing");
        jsonObject.put("thread", "Main");
        jsonObject.put("timestamp", "2019-07-29T09:12:33.001Z");
        jsonObject.put("logger", "com.example.foo");
        jsonObject.put("level", "DEBUG");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id","12345678bfxsfdsf");
        jsonObject2.put("message","Testing");
        jsonObject2.put("thread", "Main");
        jsonObject2.put("timestamp", "2019-09-29T09:12:33.001Z");
        jsonObject2.put("logger", "com.example.foo");
        jsonObject2.put("level", "DEBUG");

        req.setContentType("application/json");
        req.setContent(jsonObject.toString().getBytes("utf-8"));

        LogsServlet logsServlet = new LogsServlet();
        logsServlet.setJSONLogsToNothing();
        logsServlet.doPost(req,resp);


        MockHttpServletRequest req2 = new MockHttpServletRequest();
        MockHttpServletResponse resp2 = new MockHttpServletResponse();
        req2.setContent(jsonObject2.toString().getBytes("utf-8"));
        logsServlet.doPost(req2,resp2);

        MockHttpServletRequest req3 = new MockHttpServletRequest();
        MockHttpServletResponse resp3 = new MockHttpServletResponse();
        StatsServlet statsServlet = new StatsServlet();
        statsServlet.doGet(req3,resp3);

        // Assert that each row has three columns in it.
        Map<String, List<Integer>> values = new HashMap<>();
        Document doc = Jsoup.parse(resp3.getContentAsString());

        for (Element table : doc.select("table")) {
            for (Element row : table.select("tr:gt(0)")) {
                Elements tds = row.select("td");
                ArrayList<Integer> intvalues = new ArrayList<>();
                String name = tds.get(0).text();
                int int1 = Integer.parseInt(tds.get(1).text());
                int int2 = Integer.parseInt(tds.get(2).text());
                intvalues.add(int1);
                intvalues.add(int2);
                values.put(name,intvalues);
            }
        }
        //Test that all the values are what I expected of them.
        assertEquals(1, (int) values.get("com.example.foo").get(0));
        assertEquals(1, (int) values.get("com.example.foo").get(1));
        assertEquals(0, (int) values.get("ALL").get(0));
        assertEquals(0, (int) values.get("ALL").get(1));
        assertEquals(0, (int) values.get("TRACE").get(0));
        assertEquals(0, (int) values.get("TRACE").get(1));
        assertEquals(1, (int) values.get("DEBUG").get(0));
        assertEquals(1, (int) values.get("DEBUG").get(1));
        assertEquals(0, (int) values.get("INFO").get(0));
        assertEquals(0, (int) values.get("INFO").get(1));
        assertEquals(0, (int) values.get("WARN").get(0));
        assertEquals(0, (int) values.get("WARN").get(1));
        assertEquals(0, (int) values.get("ERROR").get(0));
        assertEquals(0, (int) values.get("ERROR").get(1));
        assertEquals(0, (int) values.get("FATAL").get(0));
        assertEquals(0, (int) values.get("FATAL").get(1));
        assertEquals(0, (int) values.get("OFF").get(0));
        assertEquals(0, (int) values.get("OFF").get(1));
        assertEquals(1, (int) values.get("Main").get(0));
        assertEquals(1, (int) values.get("Main").get(1));
    }
}
