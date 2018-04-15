package cz.cvut.sindepe8.feeder.persistence;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.net.URL;

import static cz.cvut.sindepe8.feeder.persistence.DbConstants.*;

public class FeedTable {
    public static final String TABLE_FEED = "feedTable";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_FEED
            + "("
            + ID + " integer primary key autoincrement, "
            + TITLE + " text not null, "
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEED);
        onCreate(db);
    }

    public static ContentValues createContentValues(String title, String url){
        ContentValues cv = new ContentValues();
        cv.put(URL, url.toString());
        cv.put(TITLE, title);
        return cv;
    }
}
