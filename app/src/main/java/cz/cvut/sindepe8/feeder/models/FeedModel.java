package cz.cvut.sindepe8.feeder.models;

import java.net.URL;

/**
 * Created by petrs on 13-Apr-18.
 */

public class FeedModel {
    private String title;
    private URL url;
    private int id;

    public FeedModel(String title, URL url, int id){
        this.title = title;
        this.url = url;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
