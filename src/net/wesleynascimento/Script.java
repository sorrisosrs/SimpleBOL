package net.wesleynascimento;

import net.wesleynascimento.enums.ScriptStatus;

import java.io.File;

/**
 * Created by Wesley on 28/06/2014.
 */
public class Script {

    private File file;
    private ScriptStatus status = ScriptStatus.ENABLE;
    private String name;
    private Repository repository;

    public Script(String fileName, File path, Repository repository) {
        if (!fileName.endsWith(".lua")) {
            fileName = fileName + ".lua";
        }
        this.file = new File(path, fileName);

        if (!file.exists()) {
            status = ScriptStatus.MISSING;
        }

        this.repository = repository;
        name = fileName;
    }

    public File getFile(){
        return file;
    }

    public void setStatus( ScriptStatus status ){
        this.status = status;
        SimpleBOL.getInstance().getFrame().setupScriptList();
        if (!file.exists()) {
            this.status = ScriptStatus.MISSING;
        }
        repository.saveConfig();
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
