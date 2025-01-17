package org.example;

import java.time.LocalDateTime;

public class LinkData {
    private int maxTransferCount;
    private int actualtransferCount;
    private String message;
    private LocalDateTime createdOn;

    public LinkData(int maxTransferCount) {
        this.maxTransferCount = maxTransferCount;
        this.createdOn = LocalDateTime.now();
        this.actualtransferCount = 0;
        this.Message = null;
    }


    public void setMaxTransferCount(int maxTransferCount) {
        this.maxTransferCount = maxTransferCount;
    }

    public String getMessage() {
        return message;
    }
}
