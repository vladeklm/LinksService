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
        var counter = 0;
        System.out.println("Print login %id% to enter, if %id% is empty, sysmet create new");
        System.out.println("Print logout to logout");
        System.out.println("Print create %fullLink% %maxTransferCount% to create new link");
        System.out.println("Print delete %shortLink% to delete link");
        System.out.println("Print stop to stop service");
        System.out.println("Print goto %shortLink% to go to link");
        System.out.println("Print update %shortLink% %maxTransferCount% to update link");
        while (true) {
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
                        gotoLink(commandParts[1]);
                        break;
                    case "update":
                        update(commandParts[1], Integer.parseInt(commandParts[2]));
                        break;
                    default:
                        System.out.println("Unknown command");

                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (isStop) {
                break;
            }
            counter++;
            if (counter >= 10) {
                linkStorage.linkControlExpired();
                counter = 0;
            }
        }
    }

    private void update(String commandPart, int i) {
        if (currentUser == null) {
            System.out.println("You are not logged in");
            return;
        }
        linkStorage.updateLink(commandPart, currentUser, i);
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
        var shortLink = linkStorage.addLink(link, currentUser, maxTransferCount);
        System.out.println("Short link: " + shortLink);
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
        if (longLink == null) {
            System.out.println("Link not found");
            return;
        }
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

