package cz.cvut.sindepe8.feeder.services;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.cvut.sindepe8.feeder.models.ArticleModel;
import cz.cvut.sindepe8.feeder.models.FeedModel;

public class FeedService {
    private static List<FeedModel> feeds;
    private static List<ArticleModel> articles;

    public static void InitService() {
        // Get feeds from the database
        feeds = new ArrayList<>();
        try {
            feeds.add(new FeedModel("Example article", new URL("https://www.mobilmania.cz/rss/sc-47/"), 1));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Get articles from the database
        articles = new ArrayList<>();

        return;
    }

    public static void RefreshArticles() throws IOException, FeedException {
        articles.clear();

        for (FeedModel feed : feeds) {
            PullArticles(feed.getUrl());
        }
    }

    private static List<ArticleModel> PullArticles(URL url)
    throws IOException, FeedException {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(url));

        List cats = feed.getCategories();
        return null;
    }

    public static List<FeedModel> GetAllFeeds(){
        return feeds.subList(0, feeds.size());
    }

    public static void AddFeed(FeedModel feedModel){

    }

    public static void RemoveFeed(FeedModel feedModel){

    }

    public static List<ArticleModel> GetAllArticles()
    {
        return articles.subList(0,articles.size());
    }

    public static ArticleModel GetArticleById(int id)
    {
        for (ArticleModel articleModel : articles)
        {
            if(articleModel.getId() == id)
                return articleModel;
        }
        return null;
    }
}
