package net.wesleynascimento;

import java.awt.*;

/**
 * Created by Wesley on 28/06/2014.
 */
public enum ScriptStatus {
    OK(Color.GREEN),
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
