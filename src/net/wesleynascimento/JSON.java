package net.wesleynascimento;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;

/**
 * Created by Administrador on 28/06/2014.
 */
public class JSON {

    public static JSONObject getJSON(String string) throws IOException {
        if( string.startsWith("http") ){
            return getJSON( new URL(string) );
        } else {
            return getJSON( new File(string) );
        }
    }

    /**
     * Read and return a JSON from file
     *
     * @param file File
     * @return  JSONObject
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject getJSON(File file) throws IOException, JSONException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int cp;
        while ((cp = reader.read()) != -1) {
            sb.append((char) cp);
        }
        return new JSONObject(sb.toString());
    }

    /**
     * Read and return a JSON from URL
     *
     * @param url URL
     * @return JSONObject
     * @throws IOException
     */
    public static JSONObject getJSON(URL url) throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        int cp;
        while ((cp = reader.read()) != -1) {
            sb.append((char) cp);
        }
        return new JSONObject( sb.toString() );
    }
}
