package org.example;

import java.util.Comparator;
import java.util.List;

public abstract class MyClient_rep {

    public abstract List<Client> readAll() throws Exception;
    public abstract void writeAll(List<Client> clients) throws Exception;

    public Client getById(int id) throws Exception {
        return readAll().stream()
                .filter(c -> c.getClientId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<String> get_k_n_short_list(int k, int n) throws Exception {
        List<Client> all = readAll();
        int from = k * n;
        int to = Math.min(all.size(), from + n);
        return all.subList(from, to)
                .stream()
                .map(ClientShort::toStringShort)
                .toList();
    }

    public int get_count() throws Exception {
        return readAll().size();
    }

    public void add(Client client) throws Exception {
        List<Client> all = readAll();
        all.add(new Client(
                client.getOrganizationName(),
                client.getTypeProperty(),
                client.getAddress(),
                client.getTelephone(),
                client.getContactPerson()
        ));
        writeAll(all);
    }

    public boolean delete(int id) throws Exception {
        List<Client> all = readAll();
        boolean removed = all.removeIf(c -> c.getClientId() == id);
        if (removed) writeAll(all);
        return removed;
    }

    public boolean replace(int id, Client updatedClient) throws Exception {
        List<Client> all = readAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getClientId() == id) {
                Client newC = new Client(
                        updatedClient.getOrganizationName(),
                        updatedClient.getTypeProperty(),
                        updatedClient.getAddress(),
                        updatedClient.getTelephone(),
                        updatedClient.getContactPerson()
                );
            
                var field = ClientShort.class.getDeclaredField("clientId");
                field.setAccessible(true);
                field.setInt(newC, id);

                all.set(i, newC);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    public void sortByField(String fieldName, boolean ascending) throws Exception {
        List<Client> all = readAll();

        Comparator<Client> comparator = switch (fieldName) {
            case "clientId" -> Comparator.comparingInt(Client::getClientId);
            case "organizationName" -> Comparator.comparing(Client::getOrganizationName, String.CASE_INSENSITIVE_ORDER);
            case "contactPerson" -> Comparator.comparing(Client::getContactPerson, String.CASE_INSENSITIVE_ORDER);
            case "telephone" -> Comparator.comparing(Client::getTelephone);
            case "typeProperty" -> Comparator.comparing(Client::getTypeProperty, String.CASE_INSENSITIVE_ORDER);
            case "address" -> Comparator.comparing(Client::getAddress, String.CASE_INSENSITIVE_ORDER);
            default -> throw new IllegalArgumentException("Неизвестное поле для сортировки: " + fieldName);
        };

        if (!ascending) {
            comparator = comparator.reversed();
        }
        all.sort(comparator);

        writeAll(all);
    }

}
