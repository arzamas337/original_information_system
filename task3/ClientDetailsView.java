package org.example.view;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.entity.Client;

public class ClientDetailsView {

    public static String render(Client client, HttpServletRequest req) {

        boolean updated = Boolean.TRUE.equals(req.getAttribute("updated"));

        String infoBlock = updated
                ? "<div class='info'>Данные получены через Observer</div>"
                : "";

        return """
            <!DOCTYPE html>
            <html lang="ru">
            <head>
                <meta charset="UTF-8">
                <title>Детали клиента</title>
                <link rel="stylesheet" href="/static/style.css">
            </head>
            <body>
                <div class="container">
                    %s
                    <h2>Детали клиента ID: %d</h2>
                    <ul class="client-details">
                        <li><strong>Организация:</strong> %s</li>
                        <li><strong>Вид собственности:</strong> %s</li>
                        <li><strong>Адрес:</strong> %s</li>
                        <li><strong>Телефон:</strong> %s</li>
                        <li><strong>Контактное лицо:</strong> %s</li>
                    </ul>
                    <div class="buttons">
                        <button type="button" class="btn secondary" onclick="goBack()">← Назад</button>
                    </div>
                </div>

                <script>
                    function goBack() {
                        window.location.href = '/clients';
                    }
                </script>
            </body>
            </html>
        """.formatted(
                infoBlock,
                client.getClientId(),
                client.getOrganizationName(),
                client.getTypeProperty(),
                client.getAddress(),
                client.getTelephone(),
                client.getContactPerson()
        );
    }
}
