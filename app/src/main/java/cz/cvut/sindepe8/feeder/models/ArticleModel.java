package cz.cvut.sindepe8.feeder.models;

public class ArticleModel {
    private String title;
    private String content;
    private String url;
    private int id;

    public ArticleModel(int id, String title, String description, String url)
    {
        this.id = id;
        this.title = title;
        this.content = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() { return content; }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}

