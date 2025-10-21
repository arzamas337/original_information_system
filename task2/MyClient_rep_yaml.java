package org.example;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class MyClient_rep_yaml extends MyClient_rep{

    private static final String FILE_PATH = "src/main/resources/clients.yaml";
    private final Yaml yaml;

    public MyClient_rep_yaml() {
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(options);
    }
    @Override
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
                client.setClientId((Integer) map.get("clientId"));
            }
            clients.add(client);
        }
        return clients;
    }
    @Override
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
}
