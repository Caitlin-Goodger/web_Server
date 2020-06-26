package test.nz.ac.vuw.swen301.a2.server;

import nz.ac.vuw.swen301.a2.server.LogsServlet;
import nz.ac.vuw.swen301.a2.server.StatsPNGServlet;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class TestStatsPNG {
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
        StatsPNGServlet statsPNGServlet = new StatsPNGServlet();
        statsPNGServlet.doGet(req3,resp3);

        assertEquals(200,resp3.getStatus());
    }

    @Test
    public void testContentTypeIsCorrect() throws ServletException, IOException {
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
        StatsPNGServlet statsPNGServlet = new StatsPNGServlet();
        statsPNGServlet.doGet(req3,resp3);

        assertEquals("image/png", resp3.getContentType().toString());
    }

    @Test
    public void testReponseContentnotNull() throws ServletException, IOException {
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
        StatsPNGServlet statsPNGServlet = new StatsPNGServlet();
        statsPNGServlet.doGet(req3,resp3);

        assertNotSame(null,resp3.getContentAsString());
    }


}
