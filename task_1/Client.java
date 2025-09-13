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
        this.organizationName = organizationName;
        this.typeProperty = typeProperty;
        this.address = address;
        this.telephone = telephone;
        this.contactPerson = contactPerson;
    }
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getTypeProperty() {
        return typeProperty;
    }

    public void setTypeProperty(String typeProperty) {
        this.typeProperty = typeProperty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

}
