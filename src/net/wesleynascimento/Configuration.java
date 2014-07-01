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

    public Configuration(SimpleBOL simpleBOL){
        this.simpleBOL = simpleBOL;
        loadConfigJSON();
    }

    private void loadConfigJSON(){
        try {

            File jsonFile = new File(simpleBOL.getCurrentDirectory(), configFile);

            if (!jsonFile.exists()) {
                jsonFile.mkdirs();
                jsonFile.createNewFile();
            }
            config = JSON.getJSON( jsonFile );
        } catch (IOException e){
            JOptionPane.showMessageDialog(simpleBOL.getFrame(), "Unable to create a new config file", "Error", JOptionPane.ERROR_MESSAGE);
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

    public void setRepositoryConfig(JSONObject json){
        getConfig().put("repositories", json);
    }
}
