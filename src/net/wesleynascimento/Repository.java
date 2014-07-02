package net.wesleynascimento;

import com.sun.istack.internal.logging.Logger;
import net.wesleynascimento.enums.DownloadType;
import net.wesleynascimento.enums.RepositoryStatus;
import net.wesleynascimento.enums.ScriptStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private RepositoryStatus status = RepositoryStatus.OK;

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
        File file = SimpleBOL.getInstance().getRepositoryManager().getRepositoriesPath();

        //Read the file from url
        try {
            URL url = new URL(string);
            return parseJSON(JSON.getJSON(url), file);
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
            return parseJSON(JSON.getJSON(file), file);
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

    public boolean parseJSON(JSONObject json, File file) {
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
                this.scripts.add(new Script(scripts.get(i).toString(), f));
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

    public void setEnable(boolean active){
        if( active ){
            this.status = RepositoryStatus.OK;
        } else {
            this.status = RepositoryStatus.DISABLE;
        }
        saveConfig();
    }

    public void remove(){

    }

    public void saveConfig(){
        Configuration configuration = SimpleBOL.getInstance().getConfiguration();
        JSONObject re = configuration.getRepositoryConfig( this );

        //There is nothing for this repository, set defaults
        if( re == null){
            return;
        }

        re.put("status", status);
        JSONArray array = new JSONArray();

        for(Script script : scripts){
            if( script.getStatus() == ScriptStatus.DESACTIVE ){
                array.put( script.getName() );
            }
        }
        re.put("disabled_scripts", array);

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
        boolean status = re.getBoolean("status");
        if( status ){
            this.status = RepositoryStatus.OK;
        } else {
            this.status = RepositoryStatus.DISABLE;
        }

        //Setup scripts status
        JSONArray disabled_scripts = re.getJSONArray("disabled_scripts");

        for(Script script : scripts){
            for( int i = 0; i < disabled_scripts.length(); i++){
                if( script.getName().equals( disabled_scripts.getString(i) ) ){
                    script.setStatus(ScriptStatus.DESACTIVE );
                }
            }
        }

        //Be carefull with this call
        SimpleBOL.getInstance().getFrame().setupRepositoryList( SimpleBOL.getInstance().getRepositoryManager().getRepositoryList() );
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
