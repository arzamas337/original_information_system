package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class MyClient_rep_json {

    private static final String FILE_PATH = "src/main/resources/clients.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public MyClient_rep_json() { }

    public List<Client> readAll() throws Exception {
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<Client>>() {}.getType();
            List<Client> list = gson.fromJson(reader, listType);
            if (list == null) {
                return new ArrayList<>();
            }

            int maxId = list.stream()
                    .mapToInt(Client::getClientId)
                    .max()
                    .orElse(0);
            ClientShort.idCounter = maxId + 1;

            return list;
        }
    }

    public void writeAll(List<Client> clients) throws Exception {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(clients, writer);
        }
    }

    public Client getById(int id) throws Exception {
        List<Client> all = readAll();
        for (Client c : all) {
            if (c.getClientId() == id) {
                return c;
            }
        }
        return null;
    }

    public List<String> get_k_n_short_list(int k, int n) throws Exception {
        List<Client> all = readAll();
        int from = k * n;
        int to = Math.min(all.size(), from + n);

        if (from >= all.size()) {
            return Collections.emptyList();
        }

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

        if (!ascending) {
            comparator = comparator.reversed();
        }

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
        if (removed) {
            writeAll(all);
        }
        return removed;
    }

    public int get_count() throws Exception {
        return readAll().size();
    }
}

