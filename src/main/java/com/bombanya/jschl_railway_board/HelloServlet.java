package com.bombanya.jschl_railway_board;

import java.io.*;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/test")
public class HelloServlet extends HttpServlet {

    @Inject
    @JMSConnectionFactory("java:/remoteJms")
    @JMSPasswordCredential(userName = "admin", password = "admin")
    private JMSContext context;

    @Resource(lookup = "java:/testQueue")
    private Queue queue;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Hello World</h1>");
        out.println("</body></html>");

        Destination destination = queue;
        for (int i = 0; i < 5; i++) {
            String msg = "Message " + i;
            context.createProducer().send(destination, msg);
        }
    }
}