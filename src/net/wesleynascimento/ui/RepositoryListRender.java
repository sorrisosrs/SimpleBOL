package net.wesleynascimento.ui;

import net.wesleynascimento.Repository;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Wesley on 28/06/2014.
 */
public class RepositoryListRender extends DefaultListCellRenderer {

    private static final Color selectedBG = new Color(20, 20, 20);
    private static final Color unselectedBG = new Color(43, 43, 43);


    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        Repository repository = (Repository) value;
        this.setFont(list.getFont());

        setText(repository.getName());
        setForeground( repository.getStatus().getColor() );

        if (isSelected) {
            setBackground(selectedBG);
        } else {
            setBackground(unselectedBG);
        }
        return this;
    }
}
