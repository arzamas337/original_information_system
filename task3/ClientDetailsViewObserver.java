package org.example.view;

import jakarta.servlet.http.HttpServletRequest;
import org.example.model.observer.Observer;

public class ClientDetailsViewObserver implements Observer {

    private final HttpServletRequest request;

    public ClientDetailsViewObserver(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void update() {
        request.setAttribute("updated", true);
    }
}
