package net.wesleynascimento;

import com.sun.istack.internal.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley on 27/06/2014.
 */
public class Repository {

    private final String JSONNAME = "bol_repo.json";
    private String error = "No one!";

    private String author;      //Author's name
    private String name;        //Repository name
    private double version;     //Repository version
    private String update_url;  //Remote url to update

    private File repositoryPath;

    private List<Script> scripts = new ArrayList<Script>();

    private JSONObject json;

    private static Logger logger = Logger.getLogger(SimpleBOL.class);

    public boolean getRepository(String repositoryPath) {
        if (repositoryPath.startsWith("http")) {
            return fromURL(repositoryPath);
        } else {
            return fromFile(repositoryPath);
        }
    }

    public boolean fromURL(String string) {
        return false;
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
        json = getJSON(file);

        if (json == null) {
            logger.severe(error);
            return false;
        }

        try {
            if (json.getString("name") == null || json.getString("author") == null || json.getDouble("version") == 0.0) {
                error = "This file is not a valid " + JSONNAME;
                logger.severe(error);
                return false;
            }

            this.author = json.getString("author");
            this.name = json.getString("name");
            this.version = json.getDouble("version");
            this.repositoryPath = file.getParentFile();

            JSONArray scripts = json.getJSONArray("scripts");

            Script temp;
            for (int i = 0; i < scripts.length(); i++) {
                this.scripts.add(new Script(scripts.get(i).toString(), repositoryPath));
            }
        } catch (JSONException e) {
            error = "Invalid " + JSONNAME + " format!";
            logger.severe(error);
            return false;
        }

        return true;
    }

    public JSONObject getJSON(File file) {
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int cp;
            while ((cp = reader.read()) != -1) {
                sb.append((char) cp);
            }
        } catch (IOException e) {
            error = "IO problem!";
            logger.severe(error);
            return null;
        }

        try {
            return new JSONObject(sb.toString());
        } catch (JSONException e) {
            error = "Invalid " + JSONNAME + " format!";
            logger.severe(error);
            return null;
        }
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

    public File getRepositoryPath() {
        return repositoryPath;
    }

    public JSONObject getJson() {
        return json;
    }
}
