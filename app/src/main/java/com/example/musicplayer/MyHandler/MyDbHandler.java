package com.example.musicplayer.MyHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.musicplayer.Audio;
import com.example.musicplayer.params;

import java.util.ArrayList;
import java.util.List;

public class MyDbHandler extends SQLiteOpenHelper {
   public MyDbHandler(Context context)
    {
        super(context, params.DB_NAME,null,params.DB_VERSION);
        Log.d("vipuldb","Sucess");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("vipuldb","Sucessful");
        String create = "CREATE TABLE "+params.TABLE_NAME + "(" + params.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + params.KEY_DATA  + " TEXT(255) PRIMARY KEY," + params.KEY_TITTLE + " TEXT(255)," + params.KEY_ALBUM + " TEXT," + params.KEY_ARTIST + " TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addFav(Audio audio)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put(params.KEY_DATA,audio.getData());
        values.put(params.KEY_TITTLE,audio.getTitle());
        Log.d("vipuldbs",""+audio.getTitle());
        values.put(params.KEY_ALBUM,audio.getAlbum());
        values.put(params.KEY_ARTIST,audio.getArtist());
        db.insert(params.TABLE_NAME,null,values);
        Log.d("vipuldb","Sucessfully Added"+values.get(params.KEY_ID));
        db.close();
    }
    public List<Audio> getalldata(){
       List<Audio> favlist = new ArrayList<>();
       SQLiteDatabase db = this.getReadableDatabase();
       String select = "SELECT * FROM "+params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst())
        {
            do{
                Audio data = new Audio();
                data.setData(cursor.getString(1));
                data.setTitle(cursor.getString(2));
                data.setAlbum(cursor.getString(3));
                data.setArtist(cursor.getString(4));
                favlist.add(data);
            }while(cursor.moveToNext());
        }
        return favlist;
    }

}
