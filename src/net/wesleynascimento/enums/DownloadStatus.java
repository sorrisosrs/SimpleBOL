package net.wesleynascimento.enums;

import java.awt.*;

/**
 * Created by Wesley on 29/06/2014.
 */
public enum DownloadStatus {
    COMPLETED(new Color(71, 150, 81), "Completed"),
    ERROR(Color.RED, "Error"),
    DOWNLOADING(Color.YELLOW, "Downloading"),
    WAITING(Color.LIGHT_GRAY, "Waiting");

    private Color color;
    private String name;

    private DownloadStatus(Color color, String name) {
        this.color = color;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
