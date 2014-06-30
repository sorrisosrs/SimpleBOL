package net.wesleynascimento.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Wesley on 29/06/2014.
 */
public class Button extends JButton implements MouseListener {
    private final Color backgroundClicked = new Color(13, 41, 62);
    private final Color backgroundNormal = new Color(43, 43, 43);
    private final Color backgroundUnable = new Color(49, 51, 53);
    private final Color foregroundNormal = Color.lightGray;
    private final Color foregroundUnable = Color.darkGray;
    private boolean isOver = false;

    public Button(String label) {
        setText(label);
        setBackground(new Color(220, 220, 220));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 0);
        setBorder(border);
        addMouseListener(this);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color old = g2d.getColor();

        g2d.setColor(isOver ? backgroundClicked : backgroundNormal);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(isEnabled() ? foregroundNormal : foregroundUnable);
        g2d.setFont(getFont());

        int width = g2d.getFontMetrics().stringWidth(getText());
        g2d.drawString(getText(), (getWidth() - width) / 2, getFont().getSize());

        g2d.setColor(old);
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
        isOver = true;
    }

    public void mouseExited(MouseEvent e) {
        isOver = false;
    }
}
