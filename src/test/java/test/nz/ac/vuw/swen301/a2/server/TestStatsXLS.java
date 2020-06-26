package test.nz.ac.vuw.swen301.a2.server;

import nz.ac.vuw.swen301.a2.server.LogsServlet;
import nz.ac.vuw.swen301.a2.server.StatsXLSServlet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.io.*;

import static org.junit.Assert.*;

public class TestStatsXLS {
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
        StatsXLSServlet statsXLSServlet = new StatsXLSServlet();
        statsXLSServlet.doGet(req3,resp3);

        InputStream input = new ByteArrayInputStream(resp3.getContentAsByteArray());
        HSSFWorkbook workbook = new HSSFWorkbook(input);
        HSSFSheet sheet = workbook.getSheetAt(0);

        //Check that all the values is expected
        assertEquals("2019-07-29",sheet.getRow(0).getCell(1).toString());
        assertEquals("2019-09-29",sheet.getRow(0).getCell(2).toString());

        assertEquals("com.example.foo",sheet.getRow(1).getCell(0).toString());
        assertEquals("1.0",sheet.getRow(1).getCell(1).toString());
        assertEquals("1.0",sheet.getRow(1).getCell(2).toString());

        assertEquals("ALL",sheet.getRow(2).getCell(0).toString());
        assertEquals("0.0",sheet.getRow(2).getCell(1).toString());
        assertEquals("0.0",sheet.getRow(2).getCell(2).toString());

        assertEquals("TRACE",sheet.getRow(3).getCell(0).toString());
        assertEquals("0.0",sheet.getRow(3).getCell(1).toString());
        assertEquals("0.0",sheet.getRow(3).getCell(2).toString());

        assertEquals("DEBUG",sheet.getRow(4).getCell(0).toString());
        assertEquals("1.0",sheet.getRow(4).getCell(1).toString());
        assertEquals("1.0",sheet.getRow(4).getCell(2).toString());

        assertEquals("INFO",sheet.getRow(5).getCell(0).toString());
        assertEquals("0.0",sheet.getRow(5).getCell(1).toString());
        assertEquals("0.0",sheet.getRow(5).getCell(2).toString());

        assertEquals("WARN",sheet.getRow(6).getCell(0).toString());
        assertEquals("0.0",sheet.getRow(6).getCell(1).toString());
        assertEquals("0.0",sheet.getRow(6).getCell(2).toString());

        assertEquals("ERROR",sheet.getRow(7).getCell(0).toString());
        assertEquals("0.0",sheet.getRow(7).getCell(1).toString());
        assertEquals("0.0",sheet.getRow(7).getCell(2).toString());

        assertEquals("FATAL",sheet.getRow(8).getCell(0).toString());
        assertEquals("0.0",sheet.getRow(8).getCell(1).toString());
        assertEquals("0.0",sheet.getRow(8).getCell(2).toString());

        assertEquals("OFF",sheet.getRow(9).getCell(0).toString());
        assertEquals("0.0",sheet.getRow(9).getCell(1).toString());
        assertEquals("0.0",sheet.getRow(9).getCell(2).toString());

        assertEquals("Main",sheet.getRow(10).getCell(0).toString());
        assertEquals("1.0",sheet.getRow(10).getCell(1).toString());
        assertEquals("1.0",sheet.getRow(10).getCell(2).toString());
    }

    @Test
    public void testReturnStatusIsCorrect() throws ServletException, IOException {
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
        StatsXLSServlet statsXLSServlet = new StatsXLSServlet();
        statsXLSServlet.doGet(req3,resp3);

        assertEquals(200,resp3.getStatus());
    }

    @Test
    public void testRContentTypeIsCorrect() throws ServletException, IOException {
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
        StatsXLSServlet statsXLSServlet = new StatsXLSServlet();
        statsXLSServlet.doGet(req3,resp3);

        assertEquals("application/vnd.ms-excel",resp3.getContentType());
    }
}
