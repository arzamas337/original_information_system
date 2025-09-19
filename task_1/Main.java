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
            System.out.println("Создание клиента");
            String jsonPath = "C:/Users/user/Desktop/example.json";
            Client clientFromJson = new Client(jsonPath, true);
            System.out.println("Клиент успешно создан");
            System.out.println(clientFromJson.toStringFull());
            System.out.println(clientFromJson.toStringShort());

            System.out.println("\nСоздание клиента");
            String dataString = "ООО_X5;Частная;Москва,ул.Ленина,д.10;89161234567;Иванов Иван Иванович";
            Client clientFromString = new Client(dataString);
            System.out.println("Клиент успешно создан");
            System.out.println(clientFromString.toStringFull());

            System.out.println("\nСоздание клиента");
            System.out.print("Введите название организации: ");
            String orgName = Client.validateOrganizationName(scanner.nextLine());

            System.out.print("Введите тип собственности: ");
            String typeProperty = Client.validateTypeProperty(scanner.nextLine());

            System.out.print("Введите адрес: ");
            String address = Client.validateAddress(scanner.nextLine());

            System.out.print("Введите телефон: ");
            String telephone = Client.validateAndNormalizePhone(scanner.nextLine());

            System.out.print("Введите контактное лицо (Фамилия Имя (Отчество)): ");
            String contactPerson = Client.validateContactPerson(scanner.nextLine());

            Client clientFromConsole = new Client(orgName, typeProperty, address, telephone, contactPerson);
            System.out.println("Клиент успешно создан");
            System.out.println(clientFromConsole.toStringFull());

            Client clientA = new Client("ООО_X5", "Частная", "Москва, ул. Ленина, д.10", "89161234567", "Иванов Иван Иванович");
            Client clientB = new Client("ООО_X5", "Частная", "Москва, ул. Ленина, д.15", "89161234567", "Иванов Иван Иванович");
            System.out.println("Сравнение clientA и clientB: " + clientA.equals(clientB));

            Client clientC = new Client("ООО_X5", "Частная", "Москва, ул. Ленина, д.10", "89161234568", "Иванов Иван Иванович");
            System.out.println("Сравнение clientA и clientC: " + clientA.equals(clientC));

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
