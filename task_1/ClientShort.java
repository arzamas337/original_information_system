package org.example;

public class ClientShort {

    protected String organizationName;
    protected String contactPerson;
    protected String telephone;

    public ClientShort() {}

    public ClientShort(String organizationName, String contactPerson, String telephone) {
        this.organizationName = validateOrganizationName(organizationName);
        this.contactPerson = validateContactPerson(contactPerson);
        this.telephone = validateAndNormalizePhone(telephone);
    }

    protected static String requireNonEmptyTrimmed(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " не может быть null");
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустым");
        }
        return trimmed;
    }

    public static String validateOrganizationName(String name) {
        String trimmed = requireNonEmptyTrimmed(name, "Название организации");
        if (trimmed.length() < 2) {
            throw new IllegalArgumentException("Название организации слишком короткое (мин. 2 символа)");
        }
        if (trimmed.length() > 100) {
            throw new IllegalArgumentException("Название организации слишком длинное (макс. 100 символов)");
        }
        return trimmed;
    }

    public static String validateContactPerson(String person) {
        String trimmed = requireNonEmptyTrimmed(person, "Контактное лицо");
        if (!trimmed.matches("^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+( [А-ЯЁ][а-яё]+)?$")) {
            throw new IllegalArgumentException(
                    "Контактное лицо должно быть в формате 'Фамилия Имя (Отчество)'"
            );
        }
        return trimmed;
    }

    public static String validateAndNormalizePhone(String phone) {
        if (phone == null) {
            throw new IllegalArgumentException("Телефон не может быть null");
        }
        String cleaned = phone.replaceAll("[^0-9+]", "");
        if (cleaned.matches("8\\d{10}")) {
            return cleaned;
        }
        if (cleaned.matches("\\+7\\d{10}")) {
            return "8" + cleaned.substring(2);
        }
        if (cleaned.matches("\\d{10}")) {
            return "8" + cleaned;
        }
        throw new IllegalArgumentException("Некорректный формат номера телефона");
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getTelephone() {
        return telephone;
    }

    public String toStringShort() {
        return "ClientShort {" +
                "organizationName='" + organizationName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
