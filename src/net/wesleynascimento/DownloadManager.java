package net.wesleynascimento;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wesley on 28/06/2014.
 */
public class DownloadManager {


    private List<Download> downloadList = new ArrayList<Download>();
    private List<Download> queueList = new ArrayList<Download>();

    public void addDownload(Download download) {
        if (!queueList.contains(download)) {
            queueList.add(download);
        }
    }


}
