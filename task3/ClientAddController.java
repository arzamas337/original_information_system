package org.example.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.ClientModel;
import org.example.model.entity.Client;
import org.example.model.factory.DataSourceType;
import org.example.model.factory.RepositoryFactory;
import org.example.model.repository.MyClientRep;
import org.example.view.ClientAddView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientAddController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String source = req.getParameter("source");
        if (source == null) source = "JSON";

        resp.getWriter().print(
                ClientAddView.renderForm(new ArrayList<>(), source)
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");

        List<String> errors = new ArrayList<>();

        String organization = req.getParameter("organization");
        String telephone = req.getParameter("telephone");

        if (organization == null || organization.isBlank())
            errors.add("Организация обязательна");

        if (telephone == null || telephone.isBlank())
            errors.add("Телефон обязателен");

        String source = req.getParameter("source");
        if (source == null) source = "JSON";

        if (!errors.isEmpty()) {
            resp.getWriter().print(
                    ClientAddView.renderForm(errors, source)
            );
            return;
        }

        try {
            DataSourceType type = DataSourceType.valueOf(source);
            MyClientRep repository = RepositoryFactory.create(type);
            ClientModel model = new ClientModel(repository);

            Client client = new Client(
                    organization,
                    req.getParameter("typeProperty"),
                    req.getParameter("address"),
                    telephone,
                    req.getParameter("contactPerson")
            );

            model.add(client);

            resp.getWriter().print(ClientAddView.renderSuccess());

        } catch (Exception e) {
            errors.add("Ошибка сохранения: " + e.getMessage());
            resp.getWriter().print(
                    ClientAddView.renderForm(errors, source)
            );
        }
    }
}
