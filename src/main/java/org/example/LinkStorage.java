package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LinkStorage {
    private Map<UUID, String> userLinks;
    private Map<String, String> shortLongLinks;
    private Map<String, String> longShortLinks;
    private Map<String, LinkData> linkData;

    public LinkStorage() {
        userLinks = new HashMap<>();
        shortLongLinks = new HashMap<>();
        longShortLinks = new HashMap<>();
        linkData = new HashMap<>();
    }

    private String createShortLink(String baseUrl) {
        var id = UUID.randomUUID();
        return baseUrl + "/" + id;
    }

    public void addLink(String longLink, UUID userId, int maxTransferCount) {
        maxTransferCount = Math.min(maxTransferCount, Settings.maxTransferCount);
        var linkData = new LinkData(maxTransferCount);
        var

    }
}
