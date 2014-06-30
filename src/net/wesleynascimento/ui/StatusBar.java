package net.wesleynascimento.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Wesley on 29/06/2014.
 */
public class StatusBar extends JPanel {

    private static final long serialVersionUID = 1L;
    private final Color borderColor = new Color(86, 86, 86);
    private final Color backgroundColor = new Color(49, 51, 53);
    private final Color foregroundColor = Color.lightGray;

    protected JPanel firstPanel;
    protected JPanel secondPanel;

    public StatusBar() {
        setLayout(new BorderLayout());

        firstPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 3));
        firstPanel.setOpaque(false);
        add(firstPanel, BorderLayout.WEST);

        secondPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 5, 3));
        secondPanel.setOpaque(false);
        add(secondPanel, BorderLayout.EAST);

        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);

        Border border = BorderFactory.createLineBorder(borderColor, 1);
        this.setBorder(border);
    }

    public void addFirstPanel(JComponent component) {
        component.setForeground(foregroundColor);
        component.setOpaque(false);
        component.setFont(getFont());
        firstPanel.add(component);
    }

    public void addSecondPanel(JComponent component) {
        component.setForeground(foregroundColor);
        component.setOpaque(false);
        component.setFont(getFont());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEADING, 5, 0));
        panel.add(new Separator(borderColor));
        panel.setForeground(foregroundColor);
        panel.setOpaque(false);
        panel.setFont(getFont());
        panel.add(component);
        secondPanel.add(panel);
    }

    private class Separator extends JPanel {

        private static final long serialVersionUID = 1L;

        protected Color color;

        public Separator(Color color) {
            this.color = color;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(color);
            g.drawLine(0, 0, 0, getHeight());
        }
    }

}
