package org.example.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.ClientModel;
import org.example.model.entity.Client;
import org.example.model.factory.DataSourceType;
import org.example.model.factory.RepositoryFactory;
import org.example.model.repository.MyClientRep;

import java.io.IOException;
import java.io.PrintWriter;

public class ClientDetailsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String idParam = req.getParameter("id");
        if (idParam == null) {
            out.println("<p>Ошибка: ID клиента не указан</p>");
            return;
        }

        String sourceParam = req.getParameter("source");
        if (sourceParam == null) sourceParam = "JSON";
        DataSourceType source = DataSourceType.valueOf(sourceParam);

        MyClientRep repository = RepositoryFactory.create(source);
        ClientModel model = new ClientModel(repository);

        try {
            int id = Integer.parseInt(idParam);
            Client client = model.getById(id);

            if (client == null) {
                out.println("<p>Клиент не найден</p>");
                return;
            }

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Детали клиента</title>");
            out.println("<link rel='stylesheet' type='text/css' href='/static/style.css'>"); 
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Детали клиента ID: " + client.getClientId() + "</h2>");
            out.println("<ul class='client-details'>");
            out.println("<li>Организация: " + client.getOrganizationName() + "</li>");
            out.println("<li>Вид собственности: " + client.getTypeProperty() + "</li>");
            out.println("<li>Адрес: " + client.getAddress() + "</li>");
            out.println("<li>Телефон: " + client.getTelephone() + "</li>");
            out.println("<li>Контактное лицо: " + client.getContactPerson() + "</li>");
            out.println("</ul>");
            out.println("</body></html>");

        } catch (NumberFormatException e) {
            out.println("<p>Ошибка: некорректный ID</p>");
        } catch (Exception e) {
            out.println("<p>Ошибка: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
    }
}
