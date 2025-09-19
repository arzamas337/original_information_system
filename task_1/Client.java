public class Client {

    private static int idCounter = 1;
    private int clientId;
    private String organizationName;
    private String typeProperty;
    private String address;
    private String telephone;
    private String contactPerson;

    public Client(String organizationName, String typeProperty,
                  String address, String telephone, String contactPerson) {
        this.clientId = idCounter++;

        this.organizationName = validateOrganizationName(organizationName);
        this.typeProperty = validateTypeProperty(typeProperty);
        this.address = validateAddress(address);
        this.telephone = validateAndNormalizePhone(telephone);
        this.contactPerson = validateContactPerson(contactPerson);
    }

    private static String requireNonEmptyTrimmed(String value, String fieldName) {
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

    public static String validateContactPerson(String person) {
        String trimmed = requireNonEmptyTrimmed(person, "Контактное лицо");
        if (!trimmed.matches("^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+( [А-ЯЁ][а-яё]+)?$")) {
            throw new IllegalArgumentException(
                    "Контактное лицо должно быть в формате 'Фамилия Имя (Отчество)', каждое слово с заглавной буквы"
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
    public int getClientId() {
        return clientId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = validateOrganizationName(organizationName);
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = validateAndNormalizePhone(telephone);
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = validateContactPerson(contactPerson);
    }
}
