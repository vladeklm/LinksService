package org.example;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.UUID;

public class LinkService {
    private UUID currentUser;
    private LinkStorage linkStorage;
    private Scanner scanner;

    public LinkService() {
        linkStorage = new LinkStorage();
        scanner = new Scanner(System.in);
    }

    public void start() {
        loadSettings();
        System.out.println("LinkService started");
        var isStop = false;
        while (true) {
            System.out.println("Print login, logout, create, delete, stop, goto");
            try {
                String command = scanner.nextLine();
                String[] commandParts = command.split(" ");
                switch (commandParts[0]) {
                    case "login":
                        var id = commandParts.length > 1 ? commandParts[1] : null;
                        login(id);
                        break;
                    case "logout":
                        logout();
                        break;
                    case "create":
                        create(commandParts[1], Integer.parseInt(commandParts[2]));
                        break;
                    case "delete":
                        delete(commandParts[1]);
                        break;
                    case "stop":
                        isStop = true;
                        break;
                    case "goto":
                        break;

                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (isStop) {
                break;
            }
        }
    }

    private void login(String id) {
        currentUser = id != null ? UUID.fromString(id) : UUID.randomUUID();
        System.out.println("You are logged in as " + currentUser);
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
        try (BufferedReader reader = new BufferedReader(new FileReader("./Settings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineParts = line.split(" ");
                switch (lineParts[0]) {
                    case "baseUrl":
                        Settings.baseUrl = lineParts[1];
                        break;
                    case "maxTransferCount":
                        Settings.maxTransferCount = Integer.parseInt(lineParts[1]);
                        break;
                    case "timeToLiveInSeconds":
                        Settings.timeToLiveInSeconds = Integer.parseInt(lineParts[1]);
                        break;
                    default:
                        System.out.println("Unknown setting: " + lineParts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

