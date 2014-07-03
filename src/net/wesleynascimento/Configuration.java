package net.wesleynascimento;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Wesley on 26/06/2014.
 */
public class Configuration {

    private static final String configFile = "simple.json";
    private SimpleBOL simpleBOL;
    private JSONObject config;
    private File file;

    public Configuration(SimpleBOL simpleBOL){
        this.simpleBOL = simpleBOL;
        file = new File(simpleBOL.getCurrentDirectory(), configFile);
        loadConfigJSON();
    }

    public void savaConfigJSON(){
        try {
            JSON.createJSONFile( config, file);
        } catch (IOException e){
            JOptionPane.showMessageDialog(simpleBOL.getFrame(), "Can't save the configuration file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadConfigJSON(){
        try {
            if ( !file.exists() ) {
                JSON.createJSONFile( new JSONObject("{}"), file);
            }
            config = JSON.getJSON( file );
        } catch (IOException e){
            JOptionPane.showMessageDialog(simpleBOL.getFrame(), "Can't create a configuration file.", "Error", JOptionPane.ERROR_MESSAGE);
            config = new JSONObject("{}");
            return;
        }
    }

    public JSONObject getConfig(){
        return config;
    }

    public Object getConfig(String s){
        try {
            return config.getJSONObject(s);
        } catch (JSONException e){
            return null;
        }
    }

    public JSONObject getRepositoryConfig(Repository repository){
        try {
            return config.getJSONObject("repositories").getJSONObject(repository.getName());
        } catch (JSONException e){
            return null;
        }
    }

    public void setRepositoryConfig(String repo_key, JSONObject json){
        if( !getConfig().has("repositories") ) {
            getConfig().put("repositories", new JSONObject("{}"));
        }
        getConfig().getJSONObject("repositories").put(repo_key, json);
    }
}
