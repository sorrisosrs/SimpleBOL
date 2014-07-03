package net.wesleynascimento.ui;

import net.wesleynascimento.Download;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Wesley on 29/06/2014.
 */
public class DownloadListRender extends DefaultListCellRenderer implements ListCellRenderer<Object> {

    private static final Color selectedFG = Color.lightGray;
    private static final Color selectedBG = new Color(75, 110, 175);

    private static final Color unselectedFG = new Color(71, 150, 81);
    private static final Color unselectedBG = new Color(43, 43, 43);

    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        Download download = (Download) value;
        this.setFont(list.getFont());

        setText(download.getDisplayString());

        if (isSelected) {
            setBackground(selectedBG);
            setForeground(selectedFG);
        } else {
            setBackground(unselectedBG);
            setForeground( download.getStatus().getColor() );
        }
        return this;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
