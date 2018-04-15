package cz.cvut.sindepe8.feeder.models;

import java.net.URL;

/**
 * Created by petrs on 14-Apr-18.
 */

public class ArticleItemModel {
    private String title;
    private URL url;

    public ArticleItemModel(String title, URL url){

        this.title = title;
        this.url = url;
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
}
