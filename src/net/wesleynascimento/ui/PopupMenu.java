package net.wesleynascimento.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Wesley on 29/06/2014.
 */
public class PopupMenu extends JPopupMenu {

    private static final Color foregroundColor = Color.lightGray;
    private static final Color selectedBG = new Color(75, 110, 175);

    private static final Color borderColor = new Color(43, 43, 43);

    private static final Color unselectedBG = new Color(60, 63, 65);

    public PopupMenu() {
        this.setBackground(unselectedBG);
        this.setForeground(foregroundColor);
        Border border = BorderFactory.createLineBorder(borderColor, 1);
        this.setBorder(border);
    }

    @Override
    public JMenuItem add(JMenuItem item) {
        super.add(item);
        item.setFont(getFont());
        item.setForeground(getForeground());
        item.setBackground(getBackground());
        item.setBorder(getBorder());

        return item;
    }
}
