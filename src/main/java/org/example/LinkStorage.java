package org.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class LinkStorage {
    private Map<UUID, HashSet<String>> userLinks;
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
        var shortLink = longShortLinks.get(longLink);
        var userHasLink = false;
        maxTransferCount = Math.min(maxTransferCount, Settings.maxTransferCount);
        if (shortLink != null) {
            userHasLink = userLinks.get(userId).contains(shortLink);
            if (userHasLink) {
                var newShortLink = createShortLink(Settings.baseUrl);
                deleteLink(shortLink, userId);
                var newLinkData = linkData.get(shortLink);
                newLinkData.setMaxTransferCount(maxTransferCount);
                shortLongLinks.put(newShortLink, shortLink);
                userLinks.get(userId).add(newShortLink);
                linkData.put(newShortLink, newLinkData);
            }
        }

        if (shortLink == null || !userHasLink) {
            shortLink = createShortLink(Settings.baseUrl);
            maxTransferCount = Math.min(maxTransferCount, Settings.maxTransferCount);
            var currentLinkData = new LinkData(maxTransferCount);
            shortLongLinks.put(shortLink, shortLink);
            userLinks.get(userId).add(shortLink);
            linkData.put(shortLink, currentLinkData);
        }
    }

    public String getLongLink(String shortLink, UUID userId) {
        var userHasLink = userLinks.get(userId).contains(shortLink);
        if (userHasLink) {
            var currentLinkData = linkData.get(shortLink);
            if (currentLinkData.)
            return shortLongLinks.get(shortLink);
        }
        return null;
    }

    public void deleteLink(String shortLink, UUID userId) {
        var userHasLink = userLinks.get(userId).contains(shortLink);
        if (userHasLink) {
            userLinks.get(userId).remove(shortLink);
            var longLink = shortLongLinks.get(shortLink);
            longShortLinks.remove(longLink);
            shortLongLinks.remove(shortLink);
        }
    }

    public void updateLink(String shortLink, UUID userId, int maxTransferCount) {
        var userHasLink = userLinks.get(userId).contains(shortLink);
        if (userHasLink) {
            var currentLinkData = linkData.get(shortLink);
            currentLinkData.setMaxTransferCount(maxTransferCount);
        }
    }
}
