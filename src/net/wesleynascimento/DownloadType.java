package net.wesleynascimento;

/**
 * Created by Wesley on 28/06/2014.
 */
public enum DownloadType {
    UPDATE("[UPDATE]"), INSTALL("[INSTALL]"), UPDATE_SIMPLEBOL("[UPDATE]");

    private String title;

    private DownloadType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
