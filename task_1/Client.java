package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Client extends ClientShort {

    private static int idCounter = 1;
    private int clientId;
    private String typeProperty;
    private String address;

    public Client(String organizationName, String typeProperty,
                  String address, String telephone, String contactPerson) {
        super(organizationName, contactPerson, telephone); // валидация унаследована
        this.clientId = idCounter++;
        this.typeProperty = validateTypeProperty(typeProperty);
        this.address = validateAddress(address);
    }

    public Client(String dataString) {
        super();
        if (dataString == null || dataString.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка с данными пуста");
        }
        String[] parts = dataString.split(";");
        if (parts.length != 5) {
            throw new IllegalArgumentException(
                    "Строка должна содержать 5 параметров (организация; собственность; адрес; телефон; контактное лицо)"
            );
        }

        this.clientId = idCounter++;
        this.organizationName = validateOrganizationName(parts[0]);
        this.typeProperty = validateTypeProperty(parts[1]);
        this.address = validateAddress(parts[2]);
        this.telephone = validateAndNormalizePhone(parts[3]);
        this.contactPerson = validateContactPerson(parts[4]);
    }

    public Client(String jsonFilePath, boolean fromFile) throws IOException {
        super();
        try (FileReader reader = new FileReader(jsonFilePath)) {
            Gson gson = new Gson();
            Client temp = gson.fromJson(reader, Client.class);

            this.clientId = idCounter++;
            this.organizationName = validateOrganizationName(temp.organizationName);
            this.contactPerson = validateContactPerson(temp.contactPerson);
            this.telephone = validateAndNormalizePhone(temp.telephone);
            this.typeProperty = validateTypeProperty(temp.typeProperty);
            this.address = validateAddress(temp.address);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Ошибка в формате JSON", e);
        }
    }

    public static String validateTypeProperty(String type) {
        String trimmed = requireNonEmptyTrimmed(type, "Вид собственности");
        if (!trimmed.matches("^[A-Za-zА-Яа-яЁё]{2,50}$")) {
            throw new IllegalArgumentException("Вид собственности должен содержать только буквы (2–50 символов)");
        }
        return trimmed;
    }

    public static String validateAddress(String addr) {
        String trimmed = requireNonEmptyTrimmed(addr, "Адрес");
        if (trimmed.length() < 3) {
            throw new IllegalArgumentException("Адрес слишком короткий (мин. 3 символа)");
        }
        if (trimmed.length() > 100) {
            throw new IllegalArgumentException("Адрес слишком длинный (макс. 100 символов)");
        }
        if (!trimmed.matches("^[0-9A-Za-zА-Яа-яЁё ,.-]{3,100}$")) {
            throw new IllegalArgumentException("Адрес содержит недопустимые символы");
        }
        return trimmed;
    }

    public int getClientId() {
        return clientId;
    }

    public String getTypeProperty() {
        return typeProperty;
    }

    public void setTypeProperty(String typeProperty) {
        this.typeProperty = validateTypeProperty(typeProperty);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = validateAddress(address);
    }

    public String toStringFull() {
        return "Client {" +
                "clientId=" + clientId +
                ", organizationName='" + organizationName + '\'' +
                ", typeProperty='" + typeProperty + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(organizationName, client.organizationName) &&
                Objects.equals(contactPerson, client.contactPerson) &&
                Objects.equals(telephone, client.telephone);
    }
}
