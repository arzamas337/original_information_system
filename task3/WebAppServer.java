package org.example.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.controller.ClientListController;
import org.example.controller.ClientDetailsController;

import java.io.File;

public class WebAppServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder(new ClientListController()), "/clients");
        context.addServlet(new ServletHolder(new ClientDetailsController()), "/client");

        String projectRoot = System.getProperty("user.dir");
        String staticPath = projectRoot + "/src/main/resources/static";

        System.out.println("Looking for static files in: " + staticPath);

        File staticDir = new File(staticPath);
        if (!staticDir.exists()) {
            System.err.println("ERROR: Static directory not found: " + staticPath);
            System.err.println("Current working directory: " + projectRoot);
            staticPath = projectRoot + "/PIS2/src/main/resources/static";
            staticDir = new File(staticPath);
            if (!staticDir.exists()) {
                System.err.println("ERROR: Alternative path also not found: " + staticPath);
            }
        }

        ServletHolder staticHolder = new ServletHolder("default", DefaultServlet.class);
        staticHolder.setInitParameter("resourceBase", staticDir.getAbsolutePath());
        staticHolder.setInitParameter("dirAllowed", "true");
        staticHolder.setInitParameter("pathInfoOnly", "true");
        context.addServlet(staticHolder, "/static/*");

        server.setHandler(context);
        server.start();
        System.out.println("Server started at http://localhost:8080/clients");
        System.out.println("CSS should be at: http://localhost:8080/static/style.css");
        server.join();
    }
}
