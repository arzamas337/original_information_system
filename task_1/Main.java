import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Создание нового клиента");

        try {
            System.out.print("Введите название организации: ");
            String orgName = Client.validateOrganizationName(scanner.nextLine());

            System.out.print("Введите тип собственности: ");
            String typeProperty = Client.validateTypeProperty(scanner.nextLine());

            System.out.print("Введите адрес: ");
            String address = Client.validateAddress(scanner.nextLine());

            System.out.print("Введите телефон: ");
            String telephone = Client.validateAndNormalizePhone(scanner.nextLine());

            System.out.print("Введите контактное лицо (Фамилия Имя [Отчество]): ");
            String contactPerson = Client.validateContactPerson(scanner.nextLine());

            Client client = new Client(orgName, typeProperty, address, telephone, contactPerson);
            System.out.println("Клиент успешно создан!");
            System.out.println(client);

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

