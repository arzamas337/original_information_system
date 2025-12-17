package org.example.model;

import org.example.model.entity.Client;
import org.example.model.repository.MyClientRep;
import org.example.model.observer.Observer;
import org.example.model.observer.Subject;
import java.util.ArrayList;
import java.util.List;

public class ClientModel implements Subject {

    private final MyClientRep repository;
    private final List<Observer> observers = new ArrayList<>();

    public ClientModel(MyClientRep repository) {
        this.repository = repository;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    public List<Client> getAllClients() throws Exception {
        return repository.readAll();
    }

    public Client getById(int id) throws Exception {
        return repository.getById(id);
    }

    public void add(Client client) throws Exception {
        repository.add(client);
        notifyObservers();
    }

    public void delete(int id) throws Exception {
        repository.delete(id);
        notifyObservers();
    }

    public void replace(int id, Client client) throws Exception {
        repository.replace(id, client);
        notifyObservers();
    }
}
