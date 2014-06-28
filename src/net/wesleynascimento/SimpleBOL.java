/**
 * Copyright (c) Wesley Nascimento 2014.
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/.
 */

package net.wesleynascimento;

import net.wesleynascimento.ui.SBOLFrame;
import org.json.JSONException;
import org.json.JSONObject;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

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
    private final String update = "https://raw.githubusercontent.com/sorrisosrs/SimpleBOL/master/builds/update.json";

    private Configuration configuration;
    private RepositoryManager repositoryManager;
    private SBOLFrame frame;

    private static final SimpleBOL instance = new SimpleBOL();

    private File currentDirectory;

    public static void main(String[] args) {
        SimpleBOL.getInstance().start();
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
        lookForUpdate();

        //Look for installed repositories, and load them
        repositoryManager = new RepositoryManager(this);
        repositoryManager.loadAllRepositories();

        frame.setupRepositoryList(repositoryManager.getRepositoryList());
    }

    public void lookForUpdate(){

        //Create a parallel thread to look for updates and download
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    JSONObject jsonUpdate = JSON.getJSON( new URL( update ) );

                    if( jsonUpdate.getDouble("version") > VERSION ){
                        DownloadManager downloadManager = DownloadManager.getInstance();
                        Download download = new Download( jsonUpdate.getString("update_url"), SimpleBOL.class.getProtectionDomain().getCodeSource().getLocation().getPath(), DownloadType.UPDATE_SIMPLEBOL);
                        downloadManager.add( download );
                    }
                } catch (IOException e){
                    //Do nothing!
                } catch (JSONException e){
                    //Do nothing too
                }
            }
        }).start();
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

    public static SimpleBOL getInstance(){
        return instance;
    }

}
