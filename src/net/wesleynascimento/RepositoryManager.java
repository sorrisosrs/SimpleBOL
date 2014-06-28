package net.wesleynascimento;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley on 27/06/2014.
 */
public class RepositoryManager {

    public static final String DEFAULT_DIR = "repositories";

    private File repositoriesPath;
    private SimpleBOL simpleBOL;

    private List<Repository> repositoryList = new ArrayList<Repository>();

    public RepositoryManager(SimpleBOL simpleBOL) {
        this.simpleBOL = simpleBOL;

        repositoriesPath = new File(simpleBOL.getCurrentDirectory(), DEFAULT_DIR);

        if (!repositoriesPath.exists()) {
            if (!repositoriesPath.mkdir()) {
                JOptionPane.showMessageDialog(simpleBOL.getFrame(), "SimpleBOL can't create '" + DEFAULT_DIR + "' folder.\n Please, execute with administrator perms.", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

    public Repository getRepositoryByName(String name) {
        for (Repository r : repositoryList) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
    }

    public boolean alreadyHave(Repository repository) {
        if (repositoryList.contains(repository)) {
            return true;
        }
        return false;
    }

    public List<Repository> getRepositoryList() {
        return repositoryList;
    }

    public void loadAllRepositories() {

        Repository temp;
        for (File file : repositoriesPath.listFiles()) {
            temp = new Repository();
            if (temp.fromFile(file.getAbsolutePath())) {
                repositoryList.add(temp);
            }
        }
    }
}
