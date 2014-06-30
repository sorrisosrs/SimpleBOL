package net.wesleynascimento.ui;

import net.wesleynascimento.Download;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Wesley on 29/06/2014.
 */
public class DownloadListRender extends DefaultListCellRenderer implements ListCellRenderer<Object> {

    private static final Color selectedBG = new Color(13, 41, 62);
    private static final Color unselectedBG = new Color(43, 43, 43);

    @Override
    public Component getListCellRendererComponent(JList list,
                                                  Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        Download download = (Download) value;
        this.setFont(list.getFont());

        setText(download.getDisplayString());
        this.setForeground(download.getStatus().getColor());

        if (isSelected) {
            setBackground(selectedBG);
        } else {
            setBackground(unselectedBG);
        }
        return this;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
