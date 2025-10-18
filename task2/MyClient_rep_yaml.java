package org.example;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class MyClient_rep_yaml {

    private static final String FILE_PATH = "src/main/resources/clients.yaml";

    private final Yaml yaml;

    public MyClient_rep_yaml() {
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);
    }

    public List<Client> readAll() throws Exception {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            List<Object> data = yaml.load(reader);
            if (data == null) return new ArrayList<>();

            List<Client> clients = getClients(data);

            int maxId = clients.stream().mapToInt(Client::getClientId).max().orElse(0);
            ClientShort.idCounter = maxId + 1;

            return clients;
        }
    }

    private static List<Client> getClients(List<Object> data) throws NoSuchFieldException, IllegalAccessException {
        List<Client> clients = new ArrayList<>();
        for (Object obj : data) {
            Map<String, Object> map = (Map<String, Object>) obj;
            Client client = new Client(
                    (String) map.get("organizationName"),
                    (String) map.get("typeProperty"),
                    (String) map.get("address"),
                    (String) map.get("telephone"),
                    (String) map.get("contactPerson")
            );
            if (map.get("clientId") != null) {
                Field idField = ClientShort.class.getDeclaredField("clientId");
                idField.setAccessible(true);
                idField.setInt(client, (Integer) map.get("clientId"));
            }
            clients.add(client);
        }
        return clients;
    }

    public void writeAll(List<Client> clients) throws Exception {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            List<Map<String, Object>> data = new ArrayList<>();
            for (Client c : clients) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("clientId", c.getClientId());
                map.put("organizationName", c.getOrganizationName());
                map.put("typeProperty", c.getTypeProperty());
                map.put("address", c.getAddress());
                map.put("telephone", c.getTelephone());
                map.put("contactPerson", c.getContactPerson());
                data.add(map);
            }
            yaml.dump(data, writer);
        }
    }

    public Client getById(int id) throws Exception {
        List<Client> all = readAll();
        for (Client c : all) {
            if (c.getClientId() == id) return c;
        }
        return null;
    }

    public List<String> get_k_n_short_list(int k, int n) throws Exception {
        List<Client> all = readAll();
        int from = k * n;
        int to = Math.min(all.size(), from + n);

        if (from >= all.size()) return Collections.emptyList();

        return all.subList(from, to)
                .stream()
                .map(ClientShort::toStringShort)
                .collect(Collectors.toList());
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

        if (!ascending) comparator = comparator.reversed();

        all.sort(comparator);
        writeAll(all);
    }

    public void add(Client client) throws Exception {
        List<Client> all = readAll();
        Client newClient = new Client(
                client.getOrganizationName(),
                client.getTypeProperty(),
                client.getAddress(),
                client.getTelephone(),
                client.getContactPerson()
        );
        all.add(newClient);
        writeAll(all);
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
                Field idField = ClientShort.class.getDeclaredField("clientId");
                idField.setAccessible(true);
                idField.setInt(newC, id);

                all.set(i, newC);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) throws Exception {
        List<Client> all = readAll();
        boolean removed = all.removeIf(c -> c.getClientId() == id);
        if (removed) writeAll(all);
        return removed;
    }

    public int get_count() throws Exception {
        return readAll().size();
    }
}
