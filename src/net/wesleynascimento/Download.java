package net.wesleynascimento;

import com.littlebigberry.httpfiledownloader.FileDownloader;
import net.wesleynascimento.enums.DownloadStatus;
import net.wesleynascimento.enums.DownloadType;

import java.io.File;

/**
 * Created by Wesley on 28/06/2014.
 */
public class Download extends FileDownloader {

    private DownloadType type;
    private DownloadStatus status;

    private String displayName;
    private String displayString;

    public Download(String url, String file, DownloadType type) {
        this.setUrl(url);
        this.setLocalLocation(file);
        this.type = type;

        this.status = DownloadStatus.WAITING;

        this.setDisplayName(new File(file).getName()); //Get file name
        setDisplayString();
    }

    public void setDisplayString() {
        //Setup list display string
        StringBuilder sb = new StringBuilder();
        sb.append(getType().getTitle()).append(" ");
        sb.append(getDisplayName()).append(" - ");
        sb.append(getStatus().getName());

        if (getStatus() == DownloadStatus.DOWNLOADING) {
            sb.append(" ( ").append(getPercentComplete()).append(" ) ");
            sb.append(getKbPerSecond()).append(" kB/s");
        }
        this.displayString = sb.toString();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public DownloadType getType() {
        return type;
    }

    public DownloadStatus getStatus() {
        return status;
    }

    public void setStatus(DownloadStatus status) {
        this.status = status;
    }

    public String getDisplayString() {
        return displayString;
    }
}
