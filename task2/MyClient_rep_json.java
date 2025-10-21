package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyClient_rep_json extends MyClient_rep {

    private static final String FILE_PATH = "src/main/resources/clients.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
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
            int maxId = list.stream().mapToInt(Client::getClientId).max().orElse(0);
            ClientShort.idCounter = maxId + 1;
            return list;
        }
    }
    @Override
    public void writeAll(List<Client> clients) throws Exception {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(clients, writer);
        }
    }
}
