package net.wesleynascimento.ui;

import net.wesleynascimento.Download;
import net.wesleynascimento.DownloadManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Wesley on 29/06/2014.
 */
public class DownloadFrame extends JFrame implements Runnable {

    private final String TITLE = "Download List";

    private final Color backgroundColor = new Color(49, 51, 53);
    private final Color backgroundHightlight = new Color(43, 43, 43);

    private DownloadManager downloadManager;

    private DefaultListModel downloadList = new DefaultListModel();
    private JList list;

    public DownloadFrame() {
        this.downloadManager = DownloadManager.getInstance();

        //this.setDefaultCloseOperation(JFrame.);
        this.setTitle(TITLE);
        this.setResizable(false);
        this.setSize(280, 300);
        initComponents();
        this.setLocationRelativeTo(null);
        getContentPane().setBackground(backgroundColor);
    }

    public void initComponents() {
        //Setup default font
        Font font = new Font("Segoe UI", Font.PLAIN, 12);

        list = new JList();
        list.setBounds(5, 5, getWidth() - 15, getHeight() - 30);
        list.setFont(font);
        list.setCellRenderer(new DownloadListRender());
        list.setBackground(backgroundHightlight);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setModel(downloadList);
        list.setVisible(true);

        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.add(list);
    }

    public void addToList(Download download) {
        downloadList.addElement(download);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            new Thread(this).start();
        }
    }

    @Override
    public void run() {
        while (isVisible()) {
            //Update de List before paint the frame

            try {
                Thread.sleep(1000 / 25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.paint(getGraphics());
        }
    }
}
