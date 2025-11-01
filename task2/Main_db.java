package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Main_db {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(System.in);
        MyClient_rep_db myClient_rep_db = new MyClient_rep_db();

        while (true) {
            System.out.println("1. Показать всех клиентов");
            System.out.println("2. Добавить клиента");
            System.out.println("3. Найти клиента по ID");
            System.out.println("4. Удалить клиента по ID");
            System.out.println("5. Заменить клиента по ID");
            System.out.println("6. Получить список (часть данных)");
            System.out.println("7. Показать количество клиентов");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1 -> {
                        List<Client> clients = myClient_rep_db.readAll_clients();
                        if (clients.isEmpty()) System.out.println("Список клиентов пуст.");
                        else clients.forEach(c -> System.out.println(c.toStringFull()));
                    }
                    case 2 -> {
                        System.out.print("Название организации: ");
                        String org = scanner.nextLine();
                        System.out.print("Тип собственности: ");
                        String type = scanner.nextLine();
                        System.out.print("Адрес: ");
                        String addr = scanner.nextLine();
                        System.out.print("Телефон: ");
                        String phone = scanner.nextLine();
                        System.out.print("Контактное лицо: ");
                        String contact = scanner.nextLine();

                        myClient_rep_db.add_client(new Client(org, type, addr, phone, contact));
                        System.out.println("Клиент добавлен.");
                    }
                    case 3 -> {
                        System.out.print("Введите ID клиента: ");
                        int id = scanner.nextInt();
                        Client client = myClient_rep_db.getById_client(id);
                        System.out.println(client != null ? client.toStringFull() : "Клиент не найден.");
                    }
                    case 4 -> {
                        System.out.print("Введите ID клиента для удаления: ");
                        int delId = scanner.nextInt();
                        System.out.println(myClient_rep_db.delete_client(delId) ? "Удалено." : "Клиент не найден.");
                    }
                    case 5 -> {
                        System.out.print("Введите ID клиента для замены: ");
                        int repId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Новое название организации: ");
                        String org = scanner.nextLine();
                        System.out.print("Новый тип собственности: ");
                        String type = scanner.nextLine();
                        System.out.print("Новый адрес: ");
                        String addr = scanner.nextLine();
                        System.out.print("Новый телефон: ");
                        String phone = scanner.nextLine();
                        System.out.print("Новое контактное лицо: ");
                        String contact = scanner.nextLine();

                        boolean replaced = myClient_rep_db.replace_client(repId, new Client(org, type, addr, phone, contact));
                        System.out.println(replaced ? "Заменено." : "Клиент не найден.");
                    }
                    case 6 -> {
                        System.out.print("Введите номер страницы (k): ");
                        int k = scanner.nextInt();
                        System.out.print("Введите размер страницы (n): ");
                        int n = scanner.nextInt();

                        List<String> page = myClient_rep_db.get_k_n_short_list_clients(k, n);
                        if (page.isEmpty()) System.out.println("Нет данных для отображения.");
                        else page.forEach(System.out::println);
                    }
                    case 7 -> System.out.println("Количество клиентов: " + myClient_rep_db.get_count_clients());
                    case 0 -> {
                        System.out.println("Выход из программы...");
                        return;
                    }
                    default -> System.out.println("Неверный ввод, попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }
}
