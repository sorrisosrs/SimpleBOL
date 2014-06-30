package net.wesleynascimento.ui;

import net.wesleynascimento.DownloadManager;
import net.wesleynascimento.Repository;
import net.wesleynascimento.Script;
import net.wesleynascimento.SimpleBOL;
import net.wesleynascimento.enums.DownloadType;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * Created by Wesley on 26/06/2014.
 */
public class MainFrame extends JFrame implements ActionListener, ListSelectionListener, KeyListener {

    private final int WIDTH = 600;
    private final int HEIGHT = 420;
    private final String TITLE = "Simple BOL Repositories";

    private final Color backgroundColor = new Color(49, 51, 53);
    private final Color backgroundHightlight = new Color(43, 43, 43);

    private SimpleBOL simpleBOL;

    //Gui Components
    private TextBox repoField;
    private Button button;
    private DefaultListModel repoList = new DefaultListModel();
    private DefaultListModel scriptList = new DefaultListModel();

    public MainFrame(SimpleBOL simpleBOL) {
        this.simpleBOL = simpleBOL;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setResizable(false);
        initComponents();
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        getContentPane().setBackground(backgroundColor);
    }

    public void initComponents() {
        //Setup default font
        Font font = new Font("Segoe UI", Font.PLAIN, 12);

        repoField = new TextBox(this, "Put the repository URL here!");
        repoField.setBounds(5, 5, WIDTH - 100 - 20, 20);
        repoField.setFont(font);
        repoField.addKeyListener(this);
        repoField.setVisible(true);

        button = new Button("Clone");
        button.setBounds(WIDTH - 100 - 10, 5, 100, 20);
        button.setEnabled(false);
        button.setFont(font);
        button.setActionCommand("button_install");
        button.addActionListener(this);
        button.setVisible(true);

        JList list1 = new JList();
        list1.setBounds(5, 30, WIDTH / 2 - 5, HEIGHT - 60 - 60);
        list1.setFont(font);
        list1.setCellRenderer(new RepositoryListRender());
        list1.setBackground(backgroundHightlight);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setModel(repoList);
        list1.addListSelectionListener(this);

        JList list2 = new JList();
        list2.setBounds(WIDTH / 2 + 10, 30, WIDTH / 2 - 20, HEIGHT - 60 - 60);
        list2.setFont(font);
        list2.setBackground(backgroundHightlight);
        list2.setCellRenderer(new ScriptListRender());
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list2.setModel(scriptList);
        list2.addListSelectionListener(this);

        StatusBar statusBar = new StatusBar();
        statusBar.addFirstPanel(DownloadManager.getInstance().getStatusBarLabel());
        statusBar.addSecondPanel(simpleBOL.getScriptBuilder().getStatusBarLabel());
        statusBar.addSecondPanel(new JLabel(" v" + String.valueOf(simpleBOL.getVERSION())));
        statusBar.setBounds(0, HEIGHT - 50, WIDTH, 25);
        statusBar.setFont(font);
        statusBar.setVisible(true);

        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(repoField);
        contentPane.add(button);
        contentPane.add(list1);
        contentPane.add(list2);
        contentPane.add(statusBar);
    }

    public void setupRepositoryList(List<Repository> repositoryList) {
        //Add each repository in a row of the list!
        repoList.removeAllElements();
        scriptList.removeAllElements();

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

    public void cloneRepository() {
        if (repoField.getText().length() > 0) {
            Repository repo = new Repository();

            //If get any error
            if (!repo.fromURL(repoField.getText())) {
                JOptionPane.showMessageDialog(simpleBOL.getFrame(), repo.getError(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            //Is a valid repo, but we Already have it
            else if (simpleBOL.getRepositoryManager().alreadyHave(repo)) {
                JOptionPane.showMessageDialog(simpleBOL.getFrame(), "You already have this repository installed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            //Have not errors, so lets clone
            else {
                repo.download(DownloadType.CLONE);
            }
            repoField.setText("");
            button.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals(button.getActionCommand())) {
            cloneRepository();
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

    @Override
    public void keyTyped(KeyEvent e) {
        //keyChange(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //keyChange(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyChange(e);
    }

    public void keyChange(KeyEvent e) {
        if (e.getComponent() == repoField) {
            if (repoField.getText().length() > 0) {
                button.setEnabled(true);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    cloneRepository();
                }
            } else {
                button.setEnabled(false);
            }
        }
    }
}
