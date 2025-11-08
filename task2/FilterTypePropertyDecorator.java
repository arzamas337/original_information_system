package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class FilterTypePropertyDecorator extends MyClientRep {
    private final MyClientRep innerRepo;

    public FilterTypePropertyDecorator(MyClientRep innerRepo) {
        this.innerRepo = innerRepo;
    }

    @Override
    protected List<Client> readAll() throws Exception {
        return innerRepo.readAll()
                .stream()
                .filter(c -> "частная".equalsIgnoreCase(c.getTypeProperty()))
                .collect(Collectors.toList());
    }

    @Override
    protected void writeAll(List<Client> clients) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> get_k_n_short_list(int k, int n) throws Exception {
        List<Client> filtered = readAll();
        int from = k * n;
        int to = Math.min(filtered.size(), from + n);
        return filtered.subList(from, to)
                .stream()
                .map(Client::toStringFull)
                .toList();
    }

    @Override
    public int get_count() throws Exception {
        return readAll().size();
    }
}
