package org.example.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.ClientModel;
import org.example.model.decorator.*;
import org.example.model.entity.Client;
import org.example.model.factory.DataSourceType;
import org.example.model.factory.RepositoryFactory;
import org.example.model.repository.MyClientRep;
import org.example.view.ClientListView;

import java.io.IOException;
import java.util.List;

public class ClientListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        DataSourceType source = DataSourceType.valueOf(
                req.getParameter("source") == null ? "JSON" : req.getParameter("source")
        );

        ViewMode viewMode = ViewMode.valueOf(
                req.getParameter("view") == null ? "NONE" : req.getParameter("view")
        );

        SortMode sortMode = SortMode.valueOf(
                req.getParameter("sort") == null ? "NONE" : req.getParameter("sort")
        );

        MyClientRep repository = RepositoryFactory.create(source);
        
        switch (viewMode) {
            case PRIVATE -> repository = new FilterTypePropertyDecorator(repository);
            case STATE -> repository = new FilterTypePropertyFileDecorator(repository);
        }
        
        switch (sortMode) {
            case NAME_ASC -> repository = new SortOrganizationNameDecorator(repository);
            case NAME_DESC -> repository = new SortOrganizationNameFileDecorator(repository);
        }

        ClientModel model = new ClientModel(repository);

        try {
            List<Client> clients = model.getAllClients();
            String html = ClientListView.render(clients, source, viewMode, sortMode);
            resp.getWriter().print(html);
        } catch (Exception e) {
            resp.getWriter().print("Ошибка: " + e.getMessage());
        }
    }
}
