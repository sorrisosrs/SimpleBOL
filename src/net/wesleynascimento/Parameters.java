package net.wesleynascimento;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wesley on 26/06/2014.
 */
public class Parameters {

    Map<String, String> params = new HashMap<String, String>();

    public void parse(String[] args) {
        for (String s : args) {
            if (s.startsWith("-") && s.contains("=")) {
                s.replaceFirst("-", "");
                String[] splited = s.split("=");
                params.put(splited[0], splited[1]);
            }
        }
    }

    public boolean get(String key, boolean def) {
        if (params.containsKey(key)) {
            return Boolean.parseBoolean(params.get(key));
        }
        return def;
    }

}
