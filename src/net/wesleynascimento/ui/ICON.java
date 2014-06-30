package net.wesleynascimento.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;

/**
 * Created by Wesley on 29/06/2014.
 */
public class ICON {

    public static Icon getIcon(String resource) {
        try {
            return new ImageIcon(ImageIO.read(ICON.class.getResourceAsStream(resource)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
