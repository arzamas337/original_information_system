package org.example;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortOrganizationNameDecorator extends MyClientRep {
    private final MyClientRep innerRepo;

    public SortOrganizationNameDecorator(MyClientRep innerRepo) {
        this.innerRepo = innerRepo;
    }

    @Override
    protected List<Client> readAll() throws Exception {
        return innerRepo.readAll()
                .stream()
                .sorted(Comparator.comparing(Client::getOrganizationName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
    }

    @Override
    protected void writeAll(List<Client> clients) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> get_k_n_short_list(int k, int n) throws Exception {
        List<Client> sorted = readAll();
        int from = k * n;
        int to = Math.min(sorted.size(), from + n);
        return sorted.subList(from, to)
                .stream()
                .map(Client::toStringFull)
                .toList();
    }

    @Override
    public int get_count() throws Exception {
        return innerRepo.get_count();
    }
}
