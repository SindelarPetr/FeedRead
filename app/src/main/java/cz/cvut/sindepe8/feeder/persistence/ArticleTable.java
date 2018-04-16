package cz.cvut.sindepe8.feeder.persistence;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import static cz.cvut.sindepe8.feeder.persistence.DbConstants.CONTENT;
import static cz.cvut.sindepe8.feeder.persistence.DbConstants.ID;
import static cz.cvut.sindepe8.feeder.persistence.DbConstants.TITLE;
import static cz.cvut.sindepe8.feeder.persistence.DbConstants.URL;

/**
 * Created by petrs on 15-Apr-18.
 */

public class ArticleTable {
    public static final String TABLE_ARTICLE = "articleTable";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_ARTICLE
            + "("
            + ID + " integer primary key autoincrement, "
            + TITLE + " text not null, "
            + CONTENT + " text null, "
            + URL + " text not null "
            + ");";

    public static void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion,
                                 int newVersion) {
        dropAndCreateTable(db);
    }

    public static void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAndCreateTable(db);
    }

    public static void dropAndCreateTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLE);
        onCreate(db);
    }

    public static ContentValues createContentValues(String title, String content, String url){
        ContentValues cv = new ContentValues();
        cv.put(URL, url);
        cv.put(TITLE, title);
        cv.put(CONTENT, content);
        return cv;
    }
}
