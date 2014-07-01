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
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main frame of the program,
 * Here you can clone, see and manager Repositories and Scripts
 *
 * You can use some shorcut to do somethings:
 * Ctrol + D to show Download frame
 * Ctrol + B to build a simplobolscript
 *
 * Created by Wesley on 26/06/2014.
 */
public class MainFrame extends JFrame implements ActionListener, ListSelectionListener, KeyListener {

    private final int WIDTH = 600;
    private final int HEIGHT = 420;
    private final String TITLE = "Simple BOL Repositories";

    private final Color backgroundColor = new Color(49, 51, 53);
    private final Color backgroundHightlight = new Color(43, 43, 43);

    private SimpleBOL simpleBOL;

    private TextBox repoField;
    private Button button;
    private DefaultListModel repoList = new DefaultListModel();
    private DefaultListModel scriptList = new DefaultListModel();
    private JList list1, list2;
    private JLabel name, author, version, description, url;

    //Create a key pressedList
    private List<Integer> pressedList = new ArrayList<Integer>();

    public MainFrame(SimpleBOL simpleBOL) {
        this.simpleBOL = simpleBOL;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setResizable(false);
        initComponents();
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        this.addKeyListener(this);
        getContentPane().setBackground(backgroundColor);
    }

    /**
     * Setup all components and add into frame
     */
    public void initComponents() {
        //Default font
        Font font = new Font("Segoe UI", Font.PLAIN, 12);

        //Repository URL text field
        repoField = new TextBox(this, "Put the repository URL here!");
        repoField.setBounds(5, 5, WIDTH - 100 - 20, 20);
        repoField.setFont(font);
        repoField.addKeyListener(this);
        repoField.setVisible(true);

        //Clone button
        button = new Button("Clone");
        button.setBounds(WIDTH - 100 - 10, 5, 100, 20);
        button.setEnabled(false);
        button.setFont(font);
        button.setActionCommand("button_install");
        button.addActionListener(this);
        button.setVisible(true);

        //Repo list pop menu
        PopupMenu popList1 = new PopupMenu();
        popList1.setFont(font);
        JMenuItem item;
        item = new JMenuItem("Enable");
        item.setActionCommand("enable_repository");
        item.addActionListener( this );
        popList1.add(item);
        item = new JMenuItem("Disable");
        item.setActionCommand("disable_repository");
        item.addActionListener( this );
        popList1.add(item);
        item = new JMenuItem("Remove");
        item.setActionCommand("remove_repository");
        item.addActionListener( this );
        popList1.add(item);
        item = new JMenuItem("Clone");
        item.setActionCommand("clone_repository");
        item.addActionListener( this );
        popList1.add(item);

        //Repository list
        list1 = new JList();
        list1.setBounds(5, 30, WIDTH / 2 - 5, HEIGHT - 60 - 60);
        list1.setFont(font);
        list1.setCellRenderer(new RepositoryListRender());
        list1.setBackground(backgroundHightlight);
        list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list1.setModel(repoList);
        list1.setComponentPopupMenu(popList1);
        list1.addListSelectionListener(this);

        //Script list pop menu
        PopupMenu popList2 = new PopupMenu();
        popList2.setFont(font);
        item = new JMenuItem("Enable");
        item.setActionCommand("enable_script");
        item.addActionListener( this );
        popList2.add(item);
        item = new JMenuItem("Disable");
        item.setActionCommand("disable_script");
        item.addActionListener( this );
        popList2.add(item);
        item = new JMenuItem("Check");
        item.setActionCommand("check_script");
        item.addActionListener( this );
        popList2.add(item);

        //Script List
        list2 = new JList();
        list2.setBounds(WIDTH / 2 + 10, 30, WIDTH / 2 - 20, HEIGHT - 60 - 60);
        list2.setFont(font);
        list2.setBackground(backgroundHightlight);
        list2.setCellRenderer(new ScriptListRender());
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list2.setModel(scriptList);
        list2.setComponentPopupMenu(popList2);
        list2.addListSelectionListener(this);

        //Create information components
        name = new JLabel("Name:");
        name.setFont(font);
        name.setBounds(5, HEIGHT - 120 + 35, HEIGHT / 3, 20);
        name.setVisible(true);

        author = new JLabel("Author: ");
        author.setFont(font);
        author.setBounds(5 + HEIGHT / 3, HEIGHT - 120 + 35, HEIGHT / 3, 20);
        author.setVisible(true);

        version = new JLabel("Version: ");
        version.setFont(font);
        version.setBounds(5 + (HEIGHT / 3 * 2), HEIGHT - 120 + 35, HEIGHT / 3, 20);
        version.setVisible(true);

        description = new JLabel("Description: ");
        description.setFont(font);
        description.setBounds(5, HEIGHT - 120 + 35 + 25, HEIGHT / 3, 20);
        description.setVisible(true);

        url = new JLabel("URL: ");
        url.setFont(font);
        url.setBounds(5, HEIGHT - 120 + 35 + 50, HEIGHT / 3, 20);
        url.setVisible(true);

        //End information components

        //Bottom status bar
        StatusBar statusBar = new StatusBar();
        statusBar.addFirstPanel(DownloadManager.getInstance().getStatusBarLabel());
        statusBar.addSecondPanel(simpleBOL.getScriptBuilder().getStatusBarLabel());
        statusBar.addSecondPanel(new JLabel(" v" + String.valueOf(simpleBOL.getVERSION())));
        statusBar.setBounds(0, HEIGHT - 50, WIDTH, 25);
        statusBar.setFont(font);
        statusBar.setVisible(true);

        //Add all created Component into frame
        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(repoField);
        contentPane.add(button);
        contentPane.add(list1);
        contentPane.add(list2);
        contentPane.add(name);
        contentPane.add(author);
        contentPane.add(version);
        contentPane.add(description);
        contentPane.add(url);
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
        name.setText("Name: " + repository.getName());
        author.setText("Author: " + repository.getAuthor());
        version.setText("Version: " + repository.getVersion());
        description.setText("Description: " + repository.getDescription());
        url.setText("URL: " + repository.getUpdate_url());
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
            return;
        }

        //Repository Actions
        if( action.contains("repository") ){
            //Get the selected repository
            if( list1.isSelectionEmpty() ){
                JOptionPane.showMessageDialog(this, "There is no repository to do it.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Repository repository = (Repository) list1.getSelectedValue();

            //Clone re-download the repository!
            if( action.equals("clone_repository") ){
                repository.download( DownloadType.CLONE );
            }

            //Set repository as Enable
            else if( action.equals("enable_repository") ){
                repository.setEnable( true );
            }

            //Set repository as Disable
            else if( action.equals("disanable_repository") ){
                repository.setEnable( false );
            }

            else if( action.equals("remove_repository") ){
                repository.remove();
            }
        }

        //Scripts Actions
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        //On repoList select
        if (list1.getModel() == repoList) {
            if (!list1.isSelectionEmpty()) {

                // Find out which index is selected.
                int minIndex = list1.getMinSelectionIndex();
                int maxIndex = list1.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (list1.isSelectedIndex(i)) {
                        Repository r = (Repository) repoList.get(i);
                        setupScriptList(r);
                        loadRepositoryInfos(r);
                    }
                }
            }
        }
    }

    public boolean isPressed(int keycode) {
        return pressedList.contains(keycode);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (!pressedList.contains(e.getKeyCode())) {
            pressedList.add(e.getKeyCode());
        }

        //Check shortCuts
        if (isPressed(KeyEvent.VK_CONTROL) && isPressed(KeyEvent.VK_D)) {
            Frame frame = DownloadManager.getInstance().getFrame();

            if( !frame.isVisible() )
                frame.setVisible(true);
        }

        else if (isPressed(KeyEvent.VK_CONTROL) && isPressed(KeyEvent.VK_B)) {
            //Build
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyChange(e);

        if (pressedList.contains(e.getKeyCode())) {
            pressedList.remove(e.getKeyCode());
        }
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
