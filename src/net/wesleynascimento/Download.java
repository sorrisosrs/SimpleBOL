package net.wesleynascimento;

import com.littlebigberry.httpfiledownloader.FileDownloader;
import com.littlebigberry.httpfiledownloader.FileDownloaderDelegate;

import java.io.File;

/**
 * Created by Wesley on 28/06/2014.
 */
public class Download extends FileDownloader implements FileDownloaderDelegate {

    private DownloadType type;
    private DownloadStatus status;

    private String displayName;
    private String displayString;

    public Download(String url, String file, DownloadType type) {
        this.setDelegate(this);
        this.setUrl(url);
        this.setLocalLocation(file);
        this.type = type;

        this.status = DownloadStatus.WAITING;

        this.setDisplayName(new File(file).getName()); //Get file name

        //Setup list display string
        StringBuilder sb = new StringBuilder();
        sb.append(getType().getTitle());
        sb.append(getDisplayName()).append(" (");
        sb.append("0%").append(" ");
        sb.append("0 kb/s").append(") ");
        sb.append(getStatus().getName());

        this.displayString = sb.toString();
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public DownloadType getType() {
        return type;
    }

    public DownloadStatus getStatus() {
        return status;
    }

    @Override
    public void didStartDownload(FileDownloader fileDownloader) {
        this.status = DownloadStatus.DOWNLOADING;
    }

    @Override
    public void didProgressDownload(FileDownloader fileDownloader) {
        StringBuilder sb = new StringBuilder();
        sb.append(getType().getTitle());
        sb.append(getDisplayName()).append(" (");
        sb.append(fileDownloader.getPercentComplete()).append(" ");
        sb.append(fileDownloader.getKbPerSecond()).append(") ");
        sb.append(getStatus().getName());

        displayString = sb.toString();
    }

    @Override
    public void didFinishDownload(FileDownloader fileDownloader) {
        this.status = DownloadStatus.COMPLETED;
    }

    @Override
    public void didFailDownload(FileDownloader fileDownloader) {
        this.status = DownloadStatus.ERROR;
    }
}
