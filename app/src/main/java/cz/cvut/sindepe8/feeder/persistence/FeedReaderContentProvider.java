package cz.cvut.sindepe8.feeder.persistence;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.sun.syndication.feed.atom.Feed;

import static cz.cvut.sindepe8.feeder.persistence.DbConstants.ID;

/**
 * Created by petrs on 15-Apr-18.
 */

public class FeedReaderContentProvider extends ContentProvider {
    private FeedReadDatabaseHelper feedReadDbHelper;

    public static final String AUTHORITY = "cz.cvut.sindepe8";

    private static final int FEED_LIST = 1;
    private static final int FEED_ID = 2;
    private static final int ARTICLE_LIST = 3;
    private static final int ARTICLE_ID = 4;

    private static final String BASE_PATH = "content";
    private static final String FEED_PATH = "/feeds";
    private static final String ARTICLES_PATH = "/articles";
    public static final Uri FEEDS_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH + FEED_PATH);
    public static final Uri ARTICLES_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH + ARTICLES_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + FEED_PATH, FEED_LIST);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + FEED_PATH + "/#", FEED_ID);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + ARTICLES_PATH, ARTICLE_LIST);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + ARTICLES_PATH + "/#", ARTICLE_ID);
    }

    @Override
    public boolean onCreate() {
        feedReadDbHelper = new FeedReadDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case FEED_LIST:
                queryBuilder.setTables(FeedTable.TABLE_FEED);
                break;
            case FEED_ID:
                queryBuilder.setTables(FeedTable.TABLE_FEED);
                queryBuilder.appendWhere(ID + "=" + uri.getLastPathSegment());
                break;
            case ARTICLE_LIST:
                queryBuilder.setTables(ArticleTable.TABLE_ARTICLE);
                break;
            case ARTICLE_ID:
                queryBuilder.setTables(ArticleTable.TABLE_ARTICLE);
                queryBuilder.appendWhere(ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = feedReadDbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = feedReadDbHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case FEED_LIST:
                id = sqlDB.insert(FeedTable.TABLE_FEED, null, values);
                break;
            case ARTICLE_LIST:
                id = sqlDB.insert(ArticleTable.TABLE_ARTICLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = feedReadDbHelper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case FEED_LIST:
                rowsDeleted = sqlDB.delete(FeedTable.TABLE_FEED, selection, selectionArgs);
                break;
            case FEED_ID:
                String id = uri.getLastPathSegment();
                rowsDeleted = sqlDB.delete(FeedTable.TABLE_FEED, ID + "=" + id, null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
