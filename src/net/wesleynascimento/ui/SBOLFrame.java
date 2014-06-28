package net.wesleynascimento.ui;

import net.wesleynascimento.DownloadType;
import net.wesleynascimento.Repository;
import net.wesleynascimento.Script;
import net.wesleynascimento.SimpleBOL;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Wesley on 26/06/2014.
 */
public class SBOLFrame extends JFrame implements ActionListener, ListSelectionListener {

    private final int WIDTH = 600;
    private final int HEIGHT = 420;
    private final String TITLE = "Simple BOL Repositories";

    private SimpleBOL simpleBOL;

    //Gui Components
    private JTextField repoField;
    private JButton button;
    private DefaultListModel repoList = new DefaultListModel();
    private DefaultListModel scriptList = new DefaultListModel();

    public SBOLFrame(SimpleBOL simpleBOL) {
        this.simpleBOL = simpleBOL;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setResizable(false);
        initComponents();
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
    }

    public void initComponents() {
        //Setup default font
        Font font = new Font("Tohoma", Font.PLAIN, 14);

        repoField = new JTextField();
        repoField.setBounds(WIDTH / 2 - 200, 20, 320, 30);
        repoField.setFont(font);
        repoField.setVisible(true);

        button = new JButton();
        button.setBounds(WIDTH / 2 - 200 + 320 + 10, 20, 100, 30);
        button.setText("Install");
        button.setFont(font);
        button.setActionCommand("button_install");
        button.addActionListener(this);
        button.setVisible(true);

        JList list1 = new JList();
        list1.setBounds(10, 60, WIDTH / 2 - 20, HEIGHT - 60 - 60);
        list1.setFont(font);
        list1.setCellRenderer(new RepositoryListRender());
        list1.setBackground(Color.BLACK);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setModel(repoList);
        list1.addListSelectionListener(this);

        JList list2 = new JList();
        list2.setBounds(WIDTH / 2 + 10, 60, WIDTH / 2 - 20, HEIGHT - 60 - 60);
        list2.setFont(font);
        list2.setBackground(Color.BLACK);
        list2.setCellRenderer(new ScriptListRender());
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list2.setModel(scriptList);
        list2.addListSelectionListener(this);

        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(repoField);
        contentPane.add(button);
        contentPane.add(list1);
        contentPane.add(list2);
    }

    public void setupRepositoryList(List<Repository> repositoryList) {
        //Add each repository in a row of the list!

        for (Repository r : repositoryList) {
            repoList.addElement(r);
        }
    }

    public void setupScriptList(Repository repository) {
        //Add each script of this repository in the list
        scriptList.removeAllElements();

        for (Script s : repository.getScripts()) {
            scriptList.addElement(s);
        }
    }

    public void loadRepositoryInfos(Repository repository) {
        //Put informations about the repository
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals(button.getActionCommand())) {
            if (repoField.getText() != "") {

                //Create a parallel thread to download and
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Repository repo = new Repository();

                        //If is not a valid repo
                        if (!repo.fromURL(repoField.getText())) {
                            JOptionPane.showMessageDialog(simpleBOL.getFrame(), repo.getError(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        //Is a valid repo, but we Already have
                        else if (simpleBOL.getRepositoryManager().alreadyHave(repo)) {
                            JOptionPane.showMessageDialog(simpleBOL.getFrame(), "You already have this repository installed.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        //Have not errors, so lets download and install
                        else {
                            repo.download(DownloadType.INSTALL );
                        }
                    }

                }).start();

                repoField.setText("");
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList list = (JList) e.getSource();

        //On repoList select
        if (list.getModel() == repoList) {

            if (!list.isSelectionEmpty()) {

                // Find out which index is selected.
                int minIndex = list.getMinSelectionIndex();
                int maxIndex = list.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (list.isSelectedIndex(i)) {
                        Repository r = (Repository) repoList.get(i);
                        setupScriptList(r);
                        loadRepositoryInfos(r);
                    }
                }
            }
        }
    }
}
