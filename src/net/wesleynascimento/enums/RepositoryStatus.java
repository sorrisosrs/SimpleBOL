package net.wesleynascimento.enums;

import java.awt.*;

/**
 * Created by Administrador on 30/06/2014.
 */
public enum RepositoryStatus {
    ENABLE(new Color(71, 150, 81), true),
    DISABLE(new Color(203, 119, 48), false);

    private Color color;
    private boolean status;

    private RepositoryStatus(Color color, boolean status) {
        this.color = color;
        this.status = status;
    }

    public Color getColor() {
        return color;
    }

    public boolean getBoolean(){
        return status;
    }
}
