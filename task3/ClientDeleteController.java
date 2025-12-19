package org.example.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.ClientModel;
import org.example.model.factory.DataSourceType;
import org.example.model.factory.RepositoryFactory;
import org.example.model.repository.MyClientRep;

import java.io.IOException;

public class ClientDeleteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String sourceParam = req.getParameter("source");
        if (sourceParam == null) sourceParam = "JSON";

        try {
            DataSourceType source = DataSourceType.valueOf(sourceParam);
            MyClientRep repository = RepositoryFactory.create(source);
            ClientModel model = new ClientModel(repository);

            int id = Integer.parseInt(req.getParameter("id"));
            model.delete(id);

            resp.sendRedirect("/clients?source=" + source);

        } catch (Exception e) {
            resp.getWriter().write("Ошибка удаления: " + e.getMessage());
        }
    }
}
