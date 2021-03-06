package net.wesleynascimento.ui;

import net.wesleynascimento.Script;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Wesley on 28/06/2014.
 */
public class ScriptListRender extends DefaultListCellRenderer {

    private static final Color selectedBG = new Color(20, 20, 20);
    private static final Color unselectedBG = new Color(43, 43, 43);

    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {

        Script script = (Script) value;
        this.setFont(list.getFont());

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
