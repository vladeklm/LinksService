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
        loadSettings();
        System.out.println("LinkService started");
        while (true) {
            System.out.println("Print login, logout, create, delete, stop, goto");
            try {
                String command = System.console().readLine();
                String[] commandParts = command.split(" ");
                switch (commandParts[0]) {
                    case "login":
                        login(commandParts[1]);
                        break;
                    case "logout":
                        logout();
                        break;
                    case "create":
                        break;
                    case "delete":
                        break;
                    case "stop":
                        break;
                    case "goto":
                        break;

                }
            }
        }
    }

    private void login(String id) {
        currentUser = id != null ? UUID.fromString(id) : UUID.randomUUID();
    }

    private void logout() {
        currentUser = null;
    }

    private void create(String link, int maxTransferCount) {
        if (currentUser == null) {
            System.out.println("You are not logged in");
            return;
        }
        linkStorage.addLink(link, currentUser, maxTransferCount);

    }

    private void delete(String link) {
        if (currentUser == null) {
            System.out.println("You are not logged in");
            return;
        }
        linkStorage.deleteLink(link, currentUser);
    }

    private  void gotoLink(String link) throws URISyntaxException, IOException {
        if (currentUser == null) {
            System.out.println("You are not logged in");
            return;
        }
        var longLink = linkStorage.getLongLink(link, currentUser);
        Desktop.getDesktop().browse(new URI(longLink));
    }

    private void loadSettings() {

    }
}

