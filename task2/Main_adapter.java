package org.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main_adapter {
    public static void main(String[] args) throws Exception {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        MyClient_rep jsonRepo = new MyClient_rep_json();
        MyClient_rep yamlRepo = new MyClient_rep_yaml();
        MyClient_rep dbRepo = new MyClient_DB_Adapter(new MyClient_rep_db());

        Client newClient = new Client("ООО Бнал", "компания", "ул. Колотушкина 22", "89034546479", "Гриффин Питер");
        jsonRepo.add(newClient);
        yamlRepo.add(newClient);
        dbRepo.add(newClient);

        System.out.println("JSON clients:");
        jsonRepo.readAll().forEach(c -> System.out.println(c.getOrganizationName()));

        System.out.println("YAML clients:");
        yamlRepo.readAll().forEach(c -> System.out.println(c.getOrganizationName()));

        System.out.println("DB clients:");
        dbRepo.readAll().forEach(c -> System.out.println(c.getOrganizationName()));
    }
}
