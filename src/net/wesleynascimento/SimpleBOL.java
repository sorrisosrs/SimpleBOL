package net.wesleynascimento;

import net.wesleynascimento.ui.SBOLFrame;

import javax.swing.*;
import java.io.File;

/**
 * This class Start the Program, load the config file,
 * Create a Frame, Start an update if is needed
 * Load all installed repositories to the frame
 * <p/>
 * Created by Wesley on 26/06/2014.
 */
public class SimpleBOL {

    private boolean debug = false;
    private final double VERSION = 0.001;

    private Parameters parameters;
    private Configuration configuration;
    private RepositoryManager repositoryManager;
    private SBOLFrame frame;

    private File currentDirectory;

    public static void main(String[] args) {
        SimpleBOL simpleBOL = new SimpleBOL(args);
        simpleBOL.start();
    }

    public SimpleBOL(String[] args) {
        parameters = new Parameters();
        parameters.parse(args);

        //Setup parameters
        setDebug(parameters.get("debug", false));
    }

    public void start() {
        //Load the config file
        configuration = new Configuration();

        //Create the frame
        frame = new SBOLFrame(this);
        frame.setVisible(true);

        //Check if is a valid bol directory
        currentDirectory = new File(SimpleBOL.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
        File bol_studio = new File(currentDirectory, "BoL Studio.exe");

        //Verify the files on directory, if is not the bol directory
        //Request to download the last version of bol
        if (!bol_studio.exists()) {
            JOptionPane.showMessageDialog(frame, "Can't find 'Bol Studio.exe' in this directory.\nMake sure that SimpleBOL is in the same path and your BOL is updated.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        //Check for updates and download it in background

        //Look for installed repositories, and load them
        repositoryManager = new RepositoryManager(this);
        repositoryManager.loadAllRepositories();

        frame.setupRepositoryList(repositoryManager.getRepositoryList());
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public SBOLFrame getFrame() {
        return frame;
    }

    public RepositoryManager getRepositoryManager() {
        return repositoryManager;
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

}
