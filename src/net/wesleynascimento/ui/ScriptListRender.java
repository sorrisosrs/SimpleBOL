package net.wesleynascimento.ui;

import net.wesleynascimento.Script;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Wesley on 28/06/2014.
 */
public class ScriptListRender extends DefaultListCellRenderer {

    private static final Color selectedBG = new Color(20, 20, 20);
    private static final Color unselectedBG = Color.BLACK;

    public ScriptListRender() {
        this.setBorder(null);
    }

    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);

        Script script = (Script) value;

        setText(script.getName());
        setForeground(script.getStatus().getColor());

        if (isSelected) {
            setBackground(selectedBG);
        } else {
            setBackground(unselectedBG);
        }
        return this;
    }
}
