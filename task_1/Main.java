package org.example;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        try {
            System.out.println("\nСоздание краткого клиента");
            System.out.print("Введите название организации: ");
            String orgNameShort = scanner.nextLine();
            System.out.print("Введите телефон: ");
            String phoneShort = scanner.nextLine();
            System.out.print("Введите контактное лицо: ");
            String contactShort = scanner.nextLine();

            ClientShort shortClient = new ClientShort(orgNameShort, contactShort, phoneShort);
            System.out.println("Краткий клиент создан:");
            System.out.println(shortClient.toStringShort());

            System.out.println("\nСоздание клиента из JSON");
            String jsonPath = "C:/Users/user/Desktop/example.json";
            Client fullClientFromJson = new Client(jsonPath, true);
            System.out.println("Полный клиент создан:");
            System.out.println(fullClientFromJson.toStringFull());
            System.out.println("Краткая версия:");
            System.out.println(fullClientFromJson.toStringShort());

            System.out.println("\nСоздание клиента из строки данных");
            String dataString = "ООО_X5;Частная;Москва,ул.Ленина,д.10;89161234567;Иванов Иван Иванович";
            Client fullClientFromString = new Client(dataString);
            System.out.println("Полный клиент создан:");
            System.out.println(fullClientFromString.toStringFull());

            System.out.println("\nСоздание клиента через консоль");
            System.out.print("Введите название организации: ");
            String orgNameFull = scanner.nextLine();
            System.out.print("Введите тип собственности: ");
            String typeProperty = scanner.nextLine();
            System.out.print("Введите адрес: ");
            String address = scanner.nextLine();
            System.out.print("Введите телефон: ");
            String phoneFull = scanner.nextLine();
            System.out.print("Введите контактное лицо: ");
            String contactFull = scanner.nextLine();

            Client fullClientConsole = new Client(orgNameFull, typeProperty, address, phoneFull, contactFull);
            System.out.println("Полный клиент создан:");
            System.out.println(fullClientConsole.toStringFull());

            Client clientA = new Client("ООО_X5", "Частная", "Москва, ул. Ленина, д.15", "89161234567", "Иванов Иван Иванович");
            Client clientB = new Client("ООО_X5", "Частная", "Москва, ул. Ленина, д.15", "89161234567", "Иванов Иван Иванович");
            Client clientC = new Client("ООО_X5", "Частная", "Москва, ул. Ленина, д.15", "89161234568", "Иванов Иван Иванович");

            System.out.println("\nСравнение клиентов:");
            System.out.println("clientA и clientB : " + clientA.equals(clientB));
            System.out.println("clientA и clientC : " + clientA.equals(clientC));

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
