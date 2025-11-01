package org.example;

import java.util.List;

public class MyClient_DB_Adapter extends MyClient_rep {

    private final MyClient_rep_db dbRepository;

    public MyClient_DB_Adapter(MyClient_rep_db dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    protected List<Client> readAll() throws Exception {
        return dbRepository.readAll_clients();
    }

    @Override
    protected void writeAll(List<Client> clients) throws Exception {
        throw new UnsupportedOperationException("writeAll not supported for DB");
    }

    @Override
    public Client getById(int id) throws Exception {
        return dbRepository.getById_client(id);
    }

    @Override
    public List<String> get_k_n_short_list(int k, int n) throws Exception {
        return dbRepository.get_k_n_short_list_clients(k, n);
    }

    @Override
    public void add(Client client) throws Exception {
        dbRepository.add_client(client);
    }

    @Override
    public boolean delete(int id) throws Exception {
        return dbRepository.delete_client(id);
    }

    @Override
    public boolean replace(int id, Client updatedClient) throws Exception {
        return dbRepository.replace_client(id, updatedClient);
    }

    @Override
    public int get_count() throws Exception {
        return dbRepository.get_count_clients();
    }
}
