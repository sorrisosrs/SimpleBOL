package net.wesleynascimento;

import com.littlebigberry.httpfiledownloader.FileDownloader;
import com.littlebigberry.httpfiledownloader.FileDownloaderDelegate;
import net.wesleynascimento.enums.DownloadStatus;
import net.wesleynascimento.ui.DownloadFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley on 28/06/2014.
 */
public class DownloadManager implements FileDownloaderDelegate {

    private static final DownloadManager instance = new DownloadManager();
    private List<Download> downloadList = new ArrayList<Download>();
    private List<Download> queueList = new ArrayList<Download>();
    private DownloadFrame frame;
    private JLabel statusBarLabel;

    public DownloadManager(){
        frame = new DownloadFrame();

        statusBarLabel = new JLabel();
        statusBarLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                frame.setVisible(true);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        updateLabel();
    }

    public static DownloadManager getInstance(){
        return instance;
    }

    public void add(Download download) {
        if( downloadList.size() < 5 && !downloadList.contains( download )){
            downloadList.add(download);
            download.setDelegate(this);
            download.beginDownload();
        }

        else if (!queueList.contains(download)) {
            queueList.add(download);
            download.setDelegate(this);
        }
        updateLabel();
        frame.addToList(download);
    }

    private void next(){
        if( queueList.size() > 0){
            Download download = queueList.get(0);
            downloadList.add( download );
            download.beginDownload();
        }

        //Update the frame list
        SimpleBOL s = SimpleBOL.getInstance();
        s.getRepositoryManager().loadAllRepositories();
        s.getFrame().setupRepositoryList(s.getRepositoryManager().getRepositoryList());

        updateLabel();
    }

    private void updateLabel() {
        StringBuilder sb = new StringBuilder();
        sb.append("Donwloading ");
        sb.append( downloadList.size() );
        sb.append(" of ");
        sb.append(downloadList.size() + queueList.size());
        sb.append(" files in queue.");

        statusBarLabel.setText(sb.toString());
    }

    public JLabel getStatusBarLabel() {
        return statusBarLabel;
    }

    @Override
    public void didStartDownload(FileDownloader fileDownloader) {
        Download download = (Download) fileDownloader;
        download.setStatus( DownloadStatus.DOWNLOADING );
        download.setDisplayString();
        updateLabel();
    }

    @Override
    public void didProgressDownload(FileDownloader fileDownloader) {
        Download download = (Download) fileDownloader;
        download.setDisplayString();
    }

    @Override
    public void didFinishDownload(FileDownloader fileDownloader) {
        Download download = (Download) fileDownloader;
        download.setStatus( DownloadStatus.COMPLETED );
        download.setDisplayString();

        if( downloadList.contains( download) ) {
            downloadList.remove(download);
            next();
        }
    }

    @Override
    public void didFailDownload(FileDownloader fileDownloader) {
        Download download = (Download) fileDownloader;
        download.setStatus( DownloadStatus.ERROR );
        download.setDisplayString();

        if( downloadList.contains( download) ) {
            downloadList.remove(download);
            next();
        }
    }

    public DownloadFrame getFrame() {
        return frame;
    }
}
