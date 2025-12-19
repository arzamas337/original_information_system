package org.example.view;

import org.example.model.entity.Client;

import java.util.List;

public class ClientAddView {

    public static String renderForm(List<String> errors, String source) {

        String errorBlock = errors.isEmpty() ? "" :
                "<ul class='errors'>" +
                        errors.stream().map(e -> "<li>" + e + "</li>").reduce("", String::concat)
                        + "</ul>";

        return """
        <!DOCTYPE html>
        <html lang="ru">
        <head>
            <meta charset="UTF-8">
            <title>Добавить клиента</title>
            <link rel="stylesheet" href="/static/style.css">
        </head>
        <body>
        <div class="container">

            <h2>Добавление клиента</h2>
            %s

            <form method="post">
                <input type="hidden" name="source" value="%s">

                <ul class="client-details">
                    <li>
                        <strong>Организация *</strong><br>
                        <input name="organization">
                    </li>
                    <li>
                        <strong>Тип собственности</strong><br>
                        <input name="typeProperty">
                    </li>
                    <li>
                        <strong>Адрес</strong><br>
                        <input name="address">
                    </li>
                    <li>
                        <strong>Телефон *</strong><br>
                        <input name="telephone">
                    </li>
                    <li>
                        <strong>Контактное лицо</strong><br>
                        <input name="contactPerson">
                    </li>
                </ul>

                <div class="buttons">
                    <button type="submit">Сохранить</button>
                    <button type="button" class="secondary" onclick="goBack()">← Назад</button>
                </div>
            </form>

        </div>

        <script>
            function goBack() {
                if (window.opener) {
                    window.close();
                } else {
                    window.location.href = '/clients?source=%s';
                }
            }
        </script>

        </body>
        </html>
        """.formatted(errorBlock, source, source);
    }

    public static String renderFormWithData(List<String> errors, String source, Client client) {

        String errorBlock = errors.isEmpty() ? "" :
                "<ul class='errors'>" +
                        errors.stream().map(e -> "<li>" + e + "</li>").reduce("", String::concat)
                        + "</ul>";

        return """
        <!DOCTYPE html>
        <html lang="ru">
        <head>
            <meta charset="UTF-8">
            <title>Редактирование клиента</title>
            <link rel="stylesheet" href="/static/style.css">
        </head>
        <body>
        <div class="container">

            <h2>Редактирование клиента</h2>
            %s

            <form method="post">
                <input type="hidden" name="source" value="%s">
                <input type="hidden" name="id" value="%d">

                <ul class="client-details">
                    <li>
                        <strong>Организация *</strong><br>
                        <input name="organization" value="%s">
                    </li>
                    <li>
                        <strong>Тип собственности</strong><br>
                        <input name="typeProperty" value="%s">
                    </li>
                    <li>
                        <strong>Адрес</strong><br>
                        <input name="address" value="%s">
                    </li>
                    <li>
                        <strong>Телефон *</strong><br>
                        <input name="telephone" value="%s">
                    </li>
                    <li>
                        <strong>Контактное лицо</strong><br>
                        <input name="contactPerson" value="%s">
                    </li>
                </ul>

                <div class="buttons">
                    <button type="submit">Сохранить</button>
                    <button type="button" class="secondary" onclick="goBack()">← Назад</button>
                </div>
            </form>

        </div>

        <script>
            function goBack() {
                if (window.opener) {
                    window.close();
                } else {
                    window.location.href = '/clients?source=%s';
                }
            }
        </script>

        </body>
        </html>
        """.formatted(
                errorBlock,
                source,
                client.getClientId(),
                client.getOrganizationName(),
                client.getTypeProperty(),
                client.getAddress(),
                client.getTelephone(),
                client.getContactPerson(),
                source
        );
    }

    public static String renderSuccess() {
        return """
        <!DOCTYPE html>
        <html>
        <head><meta charset="UTF-8"></head>
        <body>
            <script>
                if (window.opener) {
                    window.opener.location.reload();
                    window.close();
                } else {
                    window.location.href = '/clients';
                }
            </script>
        </body>
        </html>
        """;
    }
}
