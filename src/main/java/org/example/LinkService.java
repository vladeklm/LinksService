package org.example;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

public class LinkService {
    private UUID currentUser;
    private LinkStorage linkStorage;

    public LinkService() {
        linkStorage = new LinkStorage();
    }

    public void start() {
        System.out.println("LinkService started");
        while (true) {
            System.out.println("Print login, logout, create, delete, stop, goto");
        }
    }

    private void login(String id) {
    }

    private void logout() {
    }

    private void create() {

    }

    private void delete() {

    }

    private  void gotoLink(String link) throws URISyntaxException, IOException {
    }
}
