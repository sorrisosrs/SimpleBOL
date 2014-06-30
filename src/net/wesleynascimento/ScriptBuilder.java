package net.wesleynascimento;

import javax.swing.*;

/**
 * Created by Wesley on 29/06/2014.
 */
public class ScriptBuilder {

    private JLabel statusBarLabel;
    private SimpleBOL simpleBOL;

    public ScriptBuilder(SimpleBOL simpleBOL) {
        this.simpleBOL = simpleBOL;
        //Pega no arquivo de configuraçoes a ultima build

        statusBarLabel = new JLabel("Never built");
    }

    public JLabel getStatusBarLabel() {
        return statusBarLabel;
    }
}
