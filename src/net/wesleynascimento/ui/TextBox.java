package net.wesleynascimento.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by Wesley on 29/06/2014.
 */
public class TextBox extends JTextField implements FocusListener {

    private static final long serialVersionUID = 1L;
    protected final JLabel placeholderLabel;
    private final Color backgroundColor = new Color(43, 43, 43);
    private final Color borderColor = new Color(49, 51, 53);
    private final Color borderColorFocus = new Color(75, 110, 175);

    public TextBox(JFrame parent, String placeholder) {
        super();
        this.placeholderLabel = new JLabel(placeholder);
        placeholderLabel.setFont(getFont());
        addFocusListener(this);

        setForeground(Color.lightGray);
        placeholderLabel.setForeground(Color.darkGray);

        parent.getContentPane().add(placeholderLabel);
        setBackground(backgroundColor);
    }

    public void setFont(Font font) {
        super.setFont(font);
        if (this.placeholderLabel != null)
            this.placeholderLabel.setFont(font);
    }

    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
        placeholderLabel.setBounds(x + 5, y + 3, w - 5, h - 5);
    }

    public void setText(String text) {
        super.setText(text);
        placeholderLabel.setVisible((text == null) || (text.length() <= 0));
    }

    @Override
    public void focusGained(FocusEvent e) {
        placeholderLabel.setVisible(false);
        Border border = BorderFactory.createLineBorder(borderColorFocus, 1);
        this.setBorder(border);
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().length() == 0) {
            placeholderLabel.setVisible(true);
        }

        Border border = BorderFactory.createLineBorder(borderColor, 1);
        this.setBorder(border);
    }
}
