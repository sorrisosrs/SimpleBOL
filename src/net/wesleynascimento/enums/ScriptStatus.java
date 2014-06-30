package net.wesleynascimento.enums;

import java.awt.*;

/**
 * Created by Wesley on 28/06/2014.
 */
public enum ScriptStatus {
    OK(new Color(71, 150, 81)),
    BROKEN(Color.RED),
    DESACTIVE(Color.YELLOW),
    MISSING(Color.LIGHT_GRAY);

    private Color color;

    private ScriptStatus(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
