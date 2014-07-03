package net.wesleynascimento;

import net.wesleynascimento.enums.ScriptStatus;
import org.json.JSONException;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Wesley on 29/06/2014.
 */
public class ScriptBuilder {

    private JLabel statusBarLabel;
    private SimpleBOL simpleBOL;

    public ScriptBuilder(SimpleBOL simpleBOL) {
        this.simpleBOL = simpleBOL;
        //Pega no arquivo de configuraçoes a ultima build
        Configuration configuration = simpleBOL.getConfiguration();

        String build = "Never built";
        try {
            long last = configuration.getConfig().getLong("last_build");
            build = "Built in " + getFormatedDate(new Date(last));
        } catch (JSONException e) {
            //DO nothing with this Exception, breath and keep calm
        }

        statusBarLabel = new JLabel(build);
        statusBarLabel.addMouseListener((new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    build();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }));
    }

    /**
     * Create a new "simple.lua" to load other scripts
     * and move all scripts to the same folder
     */
    public void build() throws IOException {
        StringBuilder builder = new StringBuilder();

        builder.append(header());
        builder.append("do\n");
        builder.append("\tfunction OnLoad()\n");
        builder.append("\t\tSendChat('Loading SimpleBOL enable repositories.')\n");
        builder.append("\t\tloadScripts()\n");
        builder.append("\t\tSendChat('SimpleBOL completed.')\n");
        builder.append("\tend\n");
        builder.append("\n");
        builder.append("\tfunction loadScripts()\n");

        for (Repository repository : simpleBOL.getRepositoryManager().getRepositoryList()) {
            if (repository.getEnable()) {
                for (Script script : repository.getScripts()) {
                    if (script.getStatus().equals(ScriptStatus.ENABLE) && move(script)) {
                        builder.append("\t\trequire('").append(script.getName().replaceAll(".lua", "")).append("')\n");
                    }
                }
            }
        }
        builder.append("\tend\n");
        builder.append("end\n");

        File file = new File(simpleBOL.getCurrentDirectory(), "scripts");

        if (!file.exists()) {
            file.mkdirs();
        }

        file = new File(file, "simple.lua");
        if (!file.exists()) {
            file.createNewFile();
        }

        //Make
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.append(builder.toString());
        writer.flush();
        writer.close();

        statusBarLabel.setText("Built in " + getFormatedDate(new Date(System.currentTimeMillis())));
        Configuration configuration = simpleBOL.getConfiguration();
        configuration.getConfig().put("last_build", System.currentTimeMillis());
        configuration.savaConfigJSON();
    }

    private boolean move(Script script) {
        File file = new File(simpleBOL.getCurrentDirectory(), "scripts" + File.separator + "foo");

        if (!file.exists()) {
            file.mkdirs();
        }

        File target = new File(file, script.getName());

        InputStream inStream;
        OutputStream outStream;
        try {
            inStream = new FileInputStream(script.getFile());
            outStream = new FileOutputStream(target);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0) {

                outStream.write(buffer, 0, length);

            }

            inStream.close();
            outStream.close();
        } catch (IOException io) {
            return false;
        }
        return true;
    }

    private String header() {
        StringBuilder sb = new StringBuilder();

        sb.append("--[[\n"); //Start a lua commentary

        sb.append("\t\t\tThis Script was auto create in {date} by SimpleBOL\n");
        sb.append("\t\t\tSimpleBol can be found here: https://github.com/sorrisosrs/SimpleBOL\n");
        sb.append("\n");
        sb.append("\t\t\tVersion: {version}\n");

        //ends commentary
        sb.append("]]\n");


        //Create the onLoad
        return sb.toString().replace("{date}", getFormatedDate(new Date(System.currentTimeMillis()))).replace("{version}", String.valueOf(simpleBOL.getVERSION()));
    }

    public String getFormatedDate(Date date) {
        SimpleDateFormat sdg = new SimpleDateFormat("H:m:s MM/dd/YYYY");
        return sdg.format(date);
    }

    public JLabel getStatusBarLabel() {
        return statusBarLabel;
    }
}
