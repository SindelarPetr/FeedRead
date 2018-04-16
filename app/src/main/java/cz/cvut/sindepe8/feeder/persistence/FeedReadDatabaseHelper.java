package cz.cvut.sindepe8.feeder.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by petrs on 15-Apr-18.
 */

public class FeedReadDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "feedread.db";
    public static final int DATABASE_VERSION = 2;

    public FeedReadDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        FeedTable.onCreate(db);
        ArticleTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        FeedTable.onUpgrade(db, oldVersion, newVersion);
        ArticleTable.onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        FeedTable.onDowngrade(db, oldVersion, newVersion);
    }
}
