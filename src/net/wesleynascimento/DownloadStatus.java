package net.wesleynascimento;

import java.awt.*;

/**
 * Created by Wesley on 28/06/2014.
 */
public enum DownloadStatus {
    COMPLETED(Color.GREEN, "Completed"),
    ERROR(Color.RED, "Error"),
    DOWNLOADING(Color.YELLOW, "Downloading"),
    WAITING(Color.LIGHT_GRAY, "Waiting");

    private Color color;
    private String name;

    private DownloadStatus(Color color, String name) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
