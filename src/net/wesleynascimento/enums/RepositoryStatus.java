package net.wesleynascimento.enums;

import java.awt.*;

/**
 * Created by Administrador on 30/06/2014.
 */
public enum RepositoryStatus {
    OK(new Color(71, 150, 81)),
    DISABLE(new Color(203, 119, 48));

    private Color color;

    private RepositoryStatus(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
