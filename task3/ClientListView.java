package org.example.view;

import org.example.model.decorator.SortMode;
import org.example.model.decorator.ViewMode;
import org.example.model.entity.Client;
import org.example.model.factory.DataSourceType;

import java.util.List;

public class ClientListView {

    public static String render(List<Client> clients,
                                DataSourceType source,
                                ViewMode viewMode,
                                SortMode sortMode) {

        StringBuilder rows = new StringBuilder();

        for (Client c : clients) {
            rows.append("""
                <tr>
                    <td>%d</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td>%s</td>
                    <td class="actions">
                        <a href="/client?id=%d&source=%s" target="_blank" class="btn btn-open">Открыть</a>
                        <a href="/client/edit?id=%d&source=%s" target="_blank" class="btn btn-edit">Редактировать</a>
                        <a href="/client/delete?id=%d&source=%s"
                           class="btn btn-delete"
                           onclick="return confirm('Удалить клиента %s ?');">🗑</a>
                    </td>
                </tr>
            """.formatted(
                    c.getClientId(),
                    c.getOrganizationName(),
                    c.getContactPerson(),
                    c.getTelephone(),
                    c.getClientId(), source,
                    c.getClientId(), source,
                    c.getClientId(), source,
                    c.getOrganizationName()
            ));
        }

        return """
            <!DOCTYPE html>
            <html lang="ru">
            <head>
                <meta charset="UTF-8">
                <title>Клиенты</title>
                <link rel="stylesheet" href="/static/style.css">
            </head>
            <body>
            <div class="container">

                <h2>Список клиентов (%s)</h2>

                <a href="/client/add?source=%s" target="_blank">
                    <button>Добавить клиента</button>
                </a>

                <form method="get" action="/clients"
                      style="display:inline-block; margin-left: 10px;">

                    <!-- источник данных -->
                    <select name="source">
                        %s
                    </select>

                    <!-- фильтр -->
                    <select name="view">
                        <option value="NONE">Без фильтра</option>
                        <option value="PRIVATE" %s>Частная собственность</option>
                        <option value="STATE" %s>Государственная</option>
                    </select>

                    <!-- сортировка -->
                    <select name="sort">
                        <option value="NONE">Без сортировки</option>
                        <option value="NAME_ASC" %s>По названию (A → Z)</option>
                        <option value="NAME_DESC" %s>По названию (Z → A)</option>
                    </select>

                    <button type="submit">Применить</button>
                </form>

                <table>
                    <tr>
                        <th>ID</th>
                        <th>Организация</th>
                        <th>Контакт</th>
                        <th>Телефон</th>
                        <th></th>
                    </tr>
                    %s
                </table>

            </div>
            </body>
            </html>
        """.formatted(
                source,
                source,
                renderSourceOptions(source),
                viewMode == ViewMode.PRIVATE ? "selected" : "",
                viewMode == ViewMode.STATE ? "selected" : "",
                sortMode == SortMode.NAME_ASC ? "selected" : "",
                sortMode == SortMode.NAME_DESC ? "selected" : "",
                rows
        );
    }

    private static String renderSourceOptions(DataSourceType current) {
        StringBuilder sb = new StringBuilder();
        for (DataSourceType t : DataSourceType.values()) {
            sb.append("""
                <option value="%s" %s>%s</option>
            """.formatted(
                    t,
                    t == current ? "selected" : "",
                    t
            ));
        }
        return sb.toString();
    }
}
