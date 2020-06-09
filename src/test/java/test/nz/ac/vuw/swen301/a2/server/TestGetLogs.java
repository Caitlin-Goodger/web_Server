package test.nz.ac.vuw.swen301.a2.server;

import nz.ac.vuw.swen301.a2.server.LogsServlet;
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
}
