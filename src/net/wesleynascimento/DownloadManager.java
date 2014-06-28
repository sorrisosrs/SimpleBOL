package net.wesleynascimento;

import com.littlebigberry.httpfiledownloader.FileDownloader;
import com.littlebigberry.httpfiledownloader.FileDownloaderDelegate;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley on 28/06/2014.
 */
public class DownloadManager implements FileDownloaderDelegate {


    private List<Download> downloadList = new ArrayList<Download>();
    private List<Download> queueList = new ArrayList<Download>();

    private static final DownloadManager instance = new DownloadManager();
    private JFrame frame;

    public DownloadManager(){
        //Cria o frame, e qualquer outra confg necessaria
    }

    public static DownloadManager getInstance(){
        return instance;
    }

    public void add(Download download) {
        if( downloadList.size() < 5 && !downloadList.contains( download )){
            downloadList.add(download);
            download.setDelegate( this );
            download.beginDownload();
        }

        else if (!queueList.contains(download)) {
            queueList.add(download);
            download.setDelegate(this);
        }
    }

    private void next(){
        if( queueList.size() > 0){
            Download download = queueList.get(0);
            downloadList.add( download );
            download.beginDownload();
        }
    }


    @Override
    public void didStartDownload(FileDownloader fileDownloader) {
        Download download = (Download) fileDownloader;
        download.setStatus( DownloadStatus.DOWNLOADING );
    }

    @Override
    public void didProgressDownload(FileDownloader fileDownloader) {
        Download download = (Download) fileDownloader;

        StringBuilder sb = new StringBuilder();
        sb.append( download.getType().getTitle());
        sb.append( download.getDisplayName()).append(" (");
        sb.append( download.getPercentComplete()).append(" ");
        sb.append( download.getKbPerSecond()).append(") ");
        sb.append( download.getStatus().getName());

        download.setDisplayString(sb.toString());
    }

    @Override
    public void didFinishDownload(FileDownloader fileDownloader) {
        Download download = (Download) fileDownloader;
        download.setStatus( DownloadStatus.COMPLETED );

        if( downloadList.contains( download) ) {
            downloadList.remove(download);
            next();
        }
    }

    @Override
    public void didFailDownload(FileDownloader fileDownloader) {
        Download download = (Download) fileDownloader;
        download.setStatus( DownloadStatus.ERROR );

        if( downloadList.contains( download) ) {
            downloadList.remove(download);
            next();
        }
    }
}
