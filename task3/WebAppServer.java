package org.example.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.example.controller.ClientEditController;
import org.example.controller.ClientListController;
import org.example.controller.ClientDetailsController;
import org.example.controller.ClientAddController;

import java.io.File;

public class WebAppServer {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context =
                new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder(new ClientListController()), "/clients");
        context.addServlet(new ServletHolder(new ClientDetailsController()), "/client");
        context.addServlet(new ServletHolder(new ClientAddController()), "/client/add");
        context.addServlet(new ServletHolder(new ClientEditController()), "/client/edit");


        String projectRoot = System.getProperty("user.dir");
        String staticPath = projectRoot + "/src/main/resources/static";

        File staticDir = new File(staticPath);

        ServletHolder staticHolder =
                new ServletHolder("default", DefaultServlet.class);
        staticHolder.setInitParameter("resourceBase", staticDir.getAbsolutePath());
        staticHolder.setInitParameter("dirAllowed", "true");
        staticHolder.setInitParameter("pathInfoOnly", "true");

        context.addServlet(staticHolder, "/static/*");

        server.setHandler(context);
        server.start();
        System.out.println("http://localhost:8080/clients");
        server.join();
    }
}
