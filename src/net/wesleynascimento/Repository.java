package net.wesleynascimento;

import com.sun.istack.internal.logging.Logger;
import net.wesleynascimento.enums.DownloadType;
import net.wesleynascimento.enums.RepositoryStatus;
import net.wesleynascimento.enums.ScriptStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley on 27/06/2014.
 */
public class Repository {

    private static final File repositoryPath = SimpleBOL.getInstance().getRepositoryManager().getRepositoriesPath();
    private static Logger logger = Logger.getLogger(SimpleBOL.class);
    private final String JSONNAME = "bol_repo.json";
    private String error = "No one!";
    private String author;      //Author's name
    private String name;        //Repository name
    private String description; //Description
    private double version;     //Repository version
    private String update_url;  //Remote url to update
    private List<Script> scripts = new ArrayList<Script>();
    private JSONObject json;
    private RepositoryStatus status = RepositoryStatus.ENABLE;

    public boolean fromURL(String string) {
        if (!string.endsWith(JSONNAME)) {
            if (!string.endsWith("/")) {
                string = string.concat("/");
            }
            string = string.concat(JSONNAME);
        }

        if (!string.startsWith("http://") && !string.startsWith("https://") && !string.startsWith("ftp://")) {
            error = "Invalid repository URL!";
            logger.severe(error);
            logger.severe(string);
            return false;
        }

        //Read the file from url
        try {
            URL url = new URL(string);
            return parseJSON(JSON.getJSON(url));
        } catch (IOException e) {
            error = "This repository can't be fount!";
            logger.severe(error);
            return false;
        } catch (JSONException e) {
            error = "Invalid " + JSONNAME + " format!";
            logger.severe(error);
            logger.severe(e.getMessage());
            return false;
        }
    }

    public boolean fromFile(String string) {
        File file = new File(string);
        logger.info("File: " + file.getAbsolutePath());

        if (!file.exists()) {
            error = "This file does not exists!";
            logger.severe(error);
            return false;
        }

        //If is a directory, look for the json file
        if (file.isDirectory()) {
            file = new File(file, JSONNAME);

            if (!file.exists()) {
                error = "This file does not exist!";
                logger.severe(error);
                return false;
            }
        } else if (file.getName() != JSONNAME) {
            error = "Can't find " + JSONNAME;
            logger.severe(error);
            return false;
        }

        //Read the file, and load the repo
        try {
            return parseJSON(JSON.getJSON(file));
        } catch (IOException e) {
            error = "IO problem!";
            logger.severe(error);
            return false;
        } catch (JSONException e) {
            error = "Invalid " + JSONNAME + " format!";
            logger.severe(error);
            logger.severe(e.getMessage());
            return false;
        }
    }

    public boolean parseJSON(JSONObject json) {
        try {
            if (json.getString("name") == null || json.getString("author") == null || json.getDouble("version") == 0.0 || json.getString("description") == null) {
                error = "This file is not a valid " + JSONNAME;
                logger.severe(error);
                return false;
            }

            this.json = json;
            this.author = json.getString("author");
            this.name = json.getString("name");
            this.version = json.getDouble("version");
            this.description = json.getString("description");
            this.update_url = json.getString("update_url");

            JSONArray scripts = json.getJSONArray("scripts");

            String thisRepoPath = repositoryPath.getAbsolutePath() + "/" + getName().replaceAll(" ", "_") + "_" + getAuthor().replaceAll(" ", "_");

            File f = new File(thisRepoPath);
            f.mkdirs();

            for (int i = 0; i < scripts.length(); i++) {
                this.scripts.add(new Script(scripts.get(i).toString(), f, this));
            }

            loadConfig();
        } catch (JSONException e) {
            error = "Invalid " + JSONNAME + " format!";
            logger.severe(error);
            logger.severe(e.getMessage());
            return false;
        }
        return true;
    }

    public void download(DownloadType downloadType) {

        //Make sure that can create it
        this.repositoryPath.mkdirs();
        String thisRepoPath = repositoryPath.getAbsolutePath() + "/" + getName().replaceAll(" ", "_") + "_" + getAuthor().replaceAll(" ", "_");

        File f = new File(thisRepoPath);
        f.mkdirs();

        DownloadManager downloadManager = DownloadManager.getInstance();

        //Create the download for the JSON file
        Download download = new Download(update_url + JSONNAME, thisRepoPath + "/" + JSONNAME, downloadType);
        downloadManager.add(download);

        //Create an download for each script
        for (Script script : scripts) {
            download = new Download(update_url + script.getName(), thisRepoPath + "/" + script.getName(), downloadType);
            downloadManager.add(download);
        }
    }

    public boolean getEnable() {
        return status.getBoolean();
    }

    public void setEnable(boolean enable) {
        if (enable) {
            this.status = RepositoryStatus.ENABLE;
        } else {
            this.status = RepositoryStatus.DISABLE;
        }
        SimpleBOL.getInstance().getFrame().setupRepositoryList(SimpleBOL.getInstance().getRepositoryManager().getRepositoryList());
        saveConfig();
    }

    public RepositoryStatus getStatus(){
        return status;
    }

    public void remove(){
        String thisRepoPath = repositoryPath.getAbsolutePath() + "/" + getName() + "_" + getAuthor();

        File f = new File(thisRepoPath);

        if (!f.exists()) {
            JOptionPane.showMessageDialog(SimpleBOL.getInstance().getFrame(), "This repository can't be found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        removeFiles(f);

        if (!f.exists()) {
            //Bad call again, I know! :0
            SimpleBOL.getInstance().getRepositoryManager().loadAllRepositories();
            SimpleBOL.getInstance().getFrame().setupRepositoryList( SimpleBOL.getInstance().getRepositoryManager().getRepositoryList() );
        } else {
            JOptionPane.showMessageDialog( SimpleBOL.getInstance().getFrame(), "Can't remove this repository now :(", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeFiles(File file) {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                removeFiles(subFile);
            }
            file.delete();
        } else {
            file.delete();
        }
    }

    public void saveConfig(){
        JSONObject myconfig = new JSONObject();
        myconfig.put("enable", status.getBoolean());

        JSONArray array = new JSONArray();

        for(Script script : scripts){
            if( script.getStatus() == ScriptStatus.DISABLE ){
                array.put( script.getName() );
            }
        }
        myconfig.put("disabled_scripts", array);

        Configuration configuration = SimpleBOL.getInstance().getConfiguration();

        configuration.setRepositoryConfig( this.getName(), myconfig );
        //write
        configuration.savaConfigJSON();
    }

    public void loadConfig(){
        Configuration configuration = SimpleBOL.getInstance().getConfiguration();
        JSONObject re = configuration.getRepositoryConfig( this );

        //There is nothing for this repository, set defaults
        if( re == null){
            return;
        }

        //Setup status
        setEnable( re.getBoolean("enable"));

        //Setup scripts status
        JSONArray disabled_scripts = re.getJSONArray("disabled_scripts");

        for(Script script : scripts){
            for( int i = 0; i < disabled_scripts.length(); i++){
                if( script.getName().equals( disabled_scripts.getString(i) ) ){
                    script.setStatus(ScriptStatus.DISABLE );
                }
            }
        }

        //Be carefull with this call
        SimpleBOL.getInstance().getFrame().setupRepositoryList( SimpleBOL.getInstance().getRepositoryManager().getRepositoryList() );
    }

    public Script getScriptByName(String name){
        for(Script script : scripts){
            if( script.getName().equals( name )){
                return script;
            }
        }

        return null;
    }

    public List<Script> getScripts() {
        return scripts;
    }

    public String getError() {
        return error;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public double getVersion() {
        return version;
    }

    public String getUpdate_url() {
        return update_url;
    }

    public String getDescription() {
        return description;
    }

    public File getRepositoryPath() {
        return repositoryPath;
    }

    public JSONObject getJson() {
        return json;
    }
}
