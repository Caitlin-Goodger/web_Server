package test.nz.ac.vuw.swen301.a2.server;
import nz.ac.vuw.swen301.a2.server.LogsServlet;
import nz.ac.vuw.swen301.a2.server.StatsCSVServlet;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

import static org.junit.Assert.*;

public class TestStatsCSV {


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
        StatsCSVServlet statsCSVServlet = new StatsCSVServlet();
        statsCSVServlet.doGet(req3,resp3);

        assertEquals("text/csv",resp3.getContentType());
    }

    @Test
    public void testReturnValuesContainsTabs() throws ServletException, IOException {
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
        StatsCSVServlet statsCSVServlet = new StatsCSVServlet();
        statsCSVServlet.doGet(req3,resp3);


        //Assert that there is at least 1 \t character in the content
        String content = resp3.getContentAsString();
        String[] contentSplit = content.split("\t");
        assertTrue(contentSplit.length>1);
    }

    @Test
    public void testReturnValuesContainsNewLines() throws ServletException, IOException {
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
        StatsCSVServlet statsCSVServlet = new StatsCSVServlet();
        statsCSVServlet.doGet(req3,resp3);

        //Assert that there is at least 1 \n character in the content
        String content = resp3.getContentAsString();
        String[] contentSplit = content.split("\n");
        assertTrue(contentSplit.length>1);
    }

    @Test
    public void testCSVValuesAreCorrect() throws ServletException, IOException {
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
        StatsCSVServlet statsCSVServlet = new StatsCSVServlet();
        statsCSVServlet.doGet(req3,resp3);

        /**
         * Get the csv file and check that the output matches what I expect.
         */
        String content = resp3.getContentAsString();
        String[] contentSplit = content.split("\n");
        assertEquals("com.example.foo" + "\t" + "2", contentSplit[1]);
        assertEquals("ALL" + "\t" + "0", contentSplit[2]);
        assertEquals("TRACE" + "\t" + "0", contentSplit[3]);
        assertEquals("DEBUG" + "\t" + "2", contentSplit[4]);
        assertEquals("INFO" + "\t" + "0", contentSplit[5]);
        assertEquals("WARN" + "\t" + "0", contentSplit[6]);
        assertEquals("ERROR" + "\t" + "0", contentSplit[7]);
        assertEquals("FATAL" + "\t" + "0", contentSplit[8]);
        assertEquals("OFF" + "\t" + "0", contentSplit[9]);
        assertEquals("Main" + "\t" + "2", contentSplit[10]);
    }

    @Test
    public void testCSVValuesAreCorrectWithDifferentDates() throws ServletException, IOException {
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
        jsonObject2.put("thread", "Maining");
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
        StatsCSVServlet statsCSVServlet = new StatsCSVServlet();
        statsCSVServlet.doGet(req3,resp3);

        /**
         * Get the csv file and check that the output matches what I expect.
         */
        String content = resp3.getContentAsString();

        String[] contentSplit = content.split("\n");
        assertEquals("com.example.foo" + "\t" + "1"+ "\t" + "1", contentSplit[1]);
        assertEquals("DEBUG" + "\t" + "1"+ "\t" + "1", contentSplit[4]);
        assertEquals("Main" + "\t" + "1"+ "\t" + "0", contentSplit[10]);
        assertEquals("Maining" + "\t" + "0"+ "\t" + "1", contentSplit[11]);
    }
}
