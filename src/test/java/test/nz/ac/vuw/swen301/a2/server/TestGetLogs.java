package test.nz.ac.vuw.swen301.a2.server;

import nz.ac.vuw.swen301.a2.server.LogsServlet;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.*;

public class TestGetLogs {

    @Test
    public void testSingleGetMethod() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        req.setParameter("level", "WARN");
        req.setParameter("limit","10");

        LogsServlet logsServlet = new LogsServlet();

        logsServlet.doGet(req,resp);
        assertEquals(200,resp.getStatus());
    }

    @Test
    public void testSingleGetNoLevel() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        req.setParameter("limit","10");

        LogsServlet logsServlet = new LogsServlet();

        logsServlet.doGet(req,resp);
        assertEquals(400,resp.getStatus());
    }

    @Test
    public void testSingleGetNoLimit() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        req.setParameter("level","WARN");

        LogsServlet logsServlet = new LogsServlet();

        logsServlet.doGet(req,resp);
        assertEquals(400,resp.getStatus());
    }

    @Test
    public void testSingleGetInvalidLimit() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        req.setParameter("level","WARN");
        req.setParameter("limit","-1");

        LogsServlet logsServlet = new LogsServlet();

        logsServlet.doGet(req,resp);
        assertEquals(400,resp.getStatus());
    }

    @Test
    public void testSingleGetINoParameters() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        LogsServlet logsServlet = new LogsServlet();

        logsServlet.doGet(req,resp);
        assertEquals(400,resp.getStatus());
    }

    @Test
    public void testGetAfterPost() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id","1234");
        jsonObject.put("message","Testing");
        jsonObject.put("thread", "Main");
        jsonObject.put("timestamp", "2019-07-29T09:12:33.001Z");
        jsonObject.put("logger", "com.example.foo");
        jsonObject.put("level", "DEBUG");

        req.setContentType("application/json");
        req.setContent(jsonObject.toString().getBytes("utf-8"));

        LogsServlet logsServlet = new LogsServlet();

        logsServlet.doPost(req,resp);

        MockHttpServletRequest getReq = new MockHttpServletRequest();
        MockHttpServletResponse getResp = new MockHttpServletResponse();

        getReq.setParameter("level", "WARN");
        getReq.setParameter("limit","10");

        logsServlet.doGet(getReq,getResp);

        assertEquals(201, resp.getStatus());
        assertEquals(200,getResp.getStatus());

    }

    @Test
    public void testSingleGetInvalidLevel() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest();
        MockHttpServletResponse resp = new MockHttpServletResponse();

        req.setParameter("level","WAR");
        req.setParameter("limit","10");

        LogsServlet logsServlet = new LogsServlet();

        logsServlet.doGet(req,resp);
        assertEquals(400,resp.getStatus());
    }
}
