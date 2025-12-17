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
import java.util.List;

public class ClientListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String sourceParam = req.getParameter("source");
        if (sourceParam == null) sourceParam = "JSON";
        DataSourceType source = DataSourceType.valueOf(sourceParam);

        MyClientRep repository = RepositoryFactory.create(source);
        ClientModel model = new ClientModel(repository);

        try {
            List<Client> clients = model.getAllClients();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Список клиентов</title>");
            out.println("<link rel='stylesheet' type='text/css' href='/static/style.css'>"); 
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Список клиентов (Источник: " + source + ")</h2>");
            out.println("<form method='get' action='/clients'>");

            out.println("Источник данных: <select name='source'>");
            for (DataSourceType type : DataSourceType.values()) {
                out.printf("<option value='%s'%s>%s</option>",
                        type,
                        type.toString().equals(source.toString()) ? " selected" : "",
                        type);
            }
            out.println("</select>");
            out.println("<input type='submit' value='Выбрать'>");
            out.println("</form><br>");

            out.println("<table>");
            out.println("<tr><th>ID</th><th>Организация</th><th>Контакт</th><th>Телефон</th><th>Детали</th></tr>");
            for (Client c : clients) {
                out.printf("<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td>" +
                                "<td><a href='/client?id=%d&source=%s' target='_blank'>Посмотреть</a></td></tr>",
                        c.getClientId(), c.getOrganizationName(), c.getContactPerson(),
                        c.getTelephone(), c.getClientId(), source);
            }
            out.println("</table>");
            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<p>Ошибка: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }
    }
}
