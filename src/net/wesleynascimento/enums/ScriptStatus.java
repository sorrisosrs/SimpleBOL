package net.wesleynascimento.enums;

import java.awt.*;

/**
 * Created by Wesley on 28/06/2014.
 */
public enum ScriptStatus {
    ENABLE(new Color(71, 150, 81)),
    BROKEN(new Color(255, 100, 100)),
    DISABLE(new Color(203, 119, 48)),
    MISSING(Color.LIGHT_GRAY);

    private Color color;

    private ScriptStatus(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
