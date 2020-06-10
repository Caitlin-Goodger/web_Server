package test.nz.ac.vuw.swen301.a2.server;

import nz.ac.vuw.swen301.a2.server.LogsServlet;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.time.*;

import static org.junit.Assert.assertEquals;

public class TestPostLogs {

    @Test
    public void testSinglePost() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","123456778");
        jsonObject.put("message","Testing");
        jsonObject.put("thread", "Main");
        jsonObject.put("timestamp", "2019-07-29T09:12:33.001Z");
        jsonObject.put("logger", "com.example.foo");
        jsonObject.put("level", "DEBUG");

        req.setContentType("application/json");
        req.setContent(jsonObject.toString().getBytes("utf-8"));

        LogsServlet logsServlet = new LogsServlet();

        logsServlet.doPost(req,resp);

        assertEquals(201,resp.getStatus());
    }

    @Test
    public void testPostDuplicate() throws ServletException, IOException {
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
        jsonObject2.put("id","1234");
        jsonObject2.put("message","Testing");
        jsonObject2.put("thread", "Main");
        jsonObject2.put("timestamp", "2019-07-29T09:12:33.001Z");
        jsonObject2.put("logger", "com.example.foo");
        jsonObject2.put("level", "DEBUG");

        req.setContentType("application/json");
        req.setContent(jsonObject.toString().getBytes("utf-8"));
        LogsServlet logsServlet = new LogsServlet();
        logsServlet.doPost(req,resp);

        MockHttpServletRequest req2 = new MockHttpServletRequest();
        MockHttpServletResponse resp2 = new MockHttpServletResponse();
        req2.setContent(jsonObject.toString().getBytes("utf-8"));
        logsServlet.doPost(req2,resp2);

        assertEquals(409,resp2.getStatus());
    }

    @Test
    public void testPostInvalidTimeStamp() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","123456778");
        jsonObject.put("message","Testing");
        jsonObject.put("thread", "Main");
        jsonObject.put("timestamp", "");
        jsonObject.put("logger", "com.example.foo");
        jsonObject.put("level", "DEBUG");

        req.setContentType("application/json");
        req.setContent(jsonObject.toString().getBytes("utf-8"));

        LogsServlet logsServlet = new LogsServlet();

        logsServlet.doPost(req,resp);

        assertEquals(400,resp.getStatus());
    }

}
