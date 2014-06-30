package net.wesleynascimento.enums;

/**
 * Created by Wesley on 29/06/2014.
 */
public enum DownloadType {
    UPDATE("[UPDATE]"), CLONE("[CLONE]"), UPDATE_SIMPLEBOL("[UPDATE]");

    private String title;

    private DownloadType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
