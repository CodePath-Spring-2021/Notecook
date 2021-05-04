package com.example.notecook.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.parse.ParseFile;

import java.util.List;

public class FavDB extends SQLiteOpenHelper {

    private static int DB_VERSION = 1;
    private static String DATABASE_NAME = "RecipeDB";
    private static String TABLE_NAME = "favoriteTable";
    public static String KEY_ID = "id";
    public static String ITEM_TITLE = "itemTitle";
    public static String ITEM_IMAGE = "itemImage";
    public static String ITEM_IMAGEURL = "itemImageUrl";
    public static String ITEM_AUTHOR = "itemAuthor";
    public static String ITEM_TIME = "itemTime";
    public static String ITEM_INGREDIENTS = "itemIngredients";
    public static String ITEM_STEPS = "itemSteps";
    public static String FAVORITE_STATUS = "fStatus";
    public static String TYPE_STATUS = "tStatus"; // 0 for post and 1 for recipe
    // dont forget write these spaces
    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " TEXT," + ITEM_TITLE + " TEXT," + ITEM_AUTHOR + " TEXT,"
            + ITEM_TIME + " TEXT," + ITEM_INGREDIENTS + " TEXT," + ITEM_STEPS + " TEXT,"
            + FAVORITE_STATUS + " TEXT," + TYPE_STATUS + "  TEXT," + ITEM_IMAGEURL + " TEXT," + ITEM_IMAGE + " BLOB);";

    public FavDB(Context context) { super(context, DATABASE_NAME,null, DB_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //DB_VERSION++;
    }

    // create empty table
    public void insertEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        // enter your value
        for (int x = 1; x < 22; x++) {
            cv.put(KEY_ID, x);
            cv.put(ITEM_TIME, "0");
            cv.put(FAVORITE_STATUS, "0");
            db.insert(TABLE_NAME,null, cv);
        }
    }

    // insert data into database FOR POST
    public void insertIntoTheDatabase(String item_title, byte[] item_image, String author, String cookTime,
                                      List<String> ingredientsList, List<String> instructionsList,
                                      String fav_status, String type_status, String id) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String ingredients = String.join("\n", ingredientsList);
        String steps = String.join("\n", instructionsList);
        cv.put(ITEM_TITLE, item_title);
        cv.put(ITEM_IMAGE, item_image);
        cv.put(ITEM_AUTHOR, author);
        cv.put(ITEM_TIME, cookTime);
        cv.put(ITEM_INGREDIENTS, ingredients);
        cv.put(ITEM_STEPS, steps);
        cv.put(FAVORITE_STATUS, fav_status);
        cv.put(TYPE_STATUS, type_status);
        cv.put(KEY_ID, id);
        db.insert(TABLE_NAME,null, cv);
        Log.d("FavDB Status", item_title + ", favstatus - " + fav_status + " - . " + cv);
    }

    // insert data into database FOR RECIPES
    public void insertIntoTheDatabase(String item_title, String item_image, String author, String cookTime,
                                      List<String> ingredientsList, List<String> instructionsList,
                                      String fav_status, String type_status, String id) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String ingredients = String.join("\n", ingredientsList);
        String steps = String.join("\n", instructionsList);
        cv.put(ITEM_TITLE, item_title);
        cv.put(ITEM_IMAGEURL, item_image);
        cv.put(ITEM_AUTHOR, author);
        cv.put(ITEM_TIME, cookTime);
        cv.put(ITEM_INGREDIENTS, ingredients);
        cv.put(ITEM_STEPS, steps);
        cv.put(FAVORITE_STATUS, fav_status);
        cv.put(TYPE_STATUS, type_status);
        cv.put(KEY_ID, id);
        db.insert(TABLE_NAME,null, cv);
        Log.d("FavDB Status", item_title + ", favstatus - " + fav_status + " - . " + cv);
    }

    // read all data
    public Cursor read_all_data(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + KEY_ID + "='" + id + "';";
        return db.rawQuery(sql,null,null);
    }

    // remove line from database
    public void remove_fav(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET  "+ FAVORITE_STATUS + " ='0' WHERE " + KEY_ID + "='" + id + "';";
        db.execSQL(sql);
        Log.d("remove", id);
    }

    // select all favorite list

    public Cursor select_all_favorite_list() {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+ TABLE_NAME + " WHERE " + FAVORITE_STATUS + " ='1'";
        return db.rawQuery(sql,null,null);
    }
}
