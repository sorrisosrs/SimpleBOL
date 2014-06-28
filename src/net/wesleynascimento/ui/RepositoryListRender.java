package net.wesleynascimento.ui;

import net.wesleynascimento.Repository;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Wesley on 28/06/2014.
 */
public class RepositoryListRender extends DefaultListCellRenderer {

    private static final Color selectedBG = new Color(20, 20, 20);
    private static final Color selectedFG = Color.WHITE;

    private static final Color unselectedBG = Color.BLACK;
    private static final Color unselectedFG = Color.WHITE;

    public RepositoryListRender() {
        this.setBorder(null);
    }

    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);

        Repository repository = (Repository) value;

        setText(repository.getName());

        if (isSelected) {
            setForeground(selectedFG);
            setBackground(selectedBG);
        } else {
            setForeground(unselectedFG);
            setBackground(unselectedBG);
        }
        return this;
    }
}
