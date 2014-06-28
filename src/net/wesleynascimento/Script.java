package net.wesleynascimento;

import java.io.File;

/**
 * Created by Wesley on 28/06/2014.
 */
public class Script {

    private File file;
    private ScriptStatus status = ScriptStatus.OK;
    private String name;

    public Script(String fileName, File path) {
        if (!fileName.endsWith(".lua")) {
            fileName = fileName + ".lua";
        }
        this.file = new File(path, fileName);

        System.out.println(file.getAbsolutePath() + " - " + fileName);

        if (!file.exists()) {
            status = ScriptStatus.MISSING;
        }

        name = fileName;
    }

    public String getName() {
        return name;
    }

    public ScriptStatus getStatus() {
        return status;
    }

    public void checkFileState() {
        //Check Lua Sintax!
    }
}
