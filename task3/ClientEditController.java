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

public class ClientEditController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");

        String source = req.getParameter("source");
        if (source == null) source = "JSON";

        int id = Integer.parseInt(req.getParameter("id"));

        try {
            DataSourceType type = DataSourceType.valueOf(source);
            MyClientRep repository = RepositoryFactory.create(type);
            ClientModel model = new ClientModel(repository);

            Client client = model.getById(id);

            resp.getWriter().print(
                    ClientAddView.renderFormWithData(new ArrayList<>(), source, client)
            );

        } catch (Exception e) {
            resp.getWriter().print("Ошибка загрузки клиента: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");

        List<String> errors = new ArrayList<>();

        int id = Integer.parseInt(req.getParameter("id"));

        String organization = req.getParameter("organization");
        String telephone = req.getParameter("telephone");

        if (organization == null || organization.isBlank())
            errors.add("Организация обязательна");

        if (telephone == null || telephone.isBlank())
            errors.add("Телефон обязателен");

        String source = req.getParameter("source");
        if (source == null) source = "JSON";

        try {
            DataSourceType type = DataSourceType.valueOf(source);
            MyClientRep repository = RepositoryFactory.create(type);
            ClientModel model = new ClientModel(repository);

            Client client = model.getById(id);

            client.setOrganizationName(organization);
            client.setTypeProperty(req.getParameter("typeProperty"));
            client.setAddress(req.getParameter("address"));
            client.setTelephone(telephone);
            client.setContactPerson(req.getParameter("contactPerson"));

            if (!errors.isEmpty()) {
                resp.getWriter().print(
                        ClientAddView.renderFormWithData(errors, source, client)
                );
                return;
            }

            model.replace(id, client);

            resp.getWriter().print(ClientAddView.renderSuccess(source));

        } catch (Exception e) {
            errors.add("Ошибка сохранения: " + e.getMessage());

            resp.getWriter().print(
                    ClientAddView.renderForm(errors, source)
            );
        }
    }
}
