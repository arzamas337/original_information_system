package org.example.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.ClientModel;
import org.example.model.decorator.FilterTypePropertyDecorator;
import org.example.model.decorator.FilterTypePropertyFileDecorator;
import org.example.model.decorator.ViewMode;
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
        
        String sourceParam = req.getParameter("source");
        if (sourceParam == null) sourceParam = "JSON";
        DataSourceType source = DataSourceType.valueOf(sourceParam);
        
        String viewParam = req.getParameter("view");
        if (viewParam == null) viewParam = "NONE";
        ViewMode viewMode = ViewMode.valueOf(viewParam);

        MyClientRep repository = RepositoryFactory.create(source);
        
        switch (viewMode) {
            case PRIVATE ->
                    repository = new FilterTypePropertyDecorator(repository);
            case STATE ->
                    repository = new FilterTypePropertyFileDecorator(repository);
            default -> { }
        }

        ClientModel model = new ClientModel(repository);

        try {
            List<Client> clients = model.getAllClients();
            String html = ClientListView.render(clients, source, viewMode);
            resp.getWriter().print(html);
        } catch (Exception e) {
            resp.getWriter().print("Ошибка: " + e.getMessage());
        }
    }
}
