package com.example.pulka001;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.pulka001.Note;

import java.util.ArrayList;

 public class DatabaseManager extends SQLiteOpenHelper {

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS notesPhotos (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, color TEXT, title TEXT, noteContent TEXT, photopath TEXT)");
    }

    public DatabaseManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean insertNote(String color, String title, String noteContent, String photopath){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS notesPhotos (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,color TEXT, title TEXT, noteContent TEXT, photopath TEXT)");
        ContentValues contentValues = new ContentValues();
        contentValues.put("color", color);
        contentValues.put("title", title);
        contentValues.put("noteContent",noteContent );
        contentValues.put("photopath", photopath);

        db.insertOrThrow("notesPhotos", null, contentValues); // gdy insert się nie powiedzie, będzie błąd
        db.close();
        return true;
    }

    public int deleteNote(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        String parsedID = id + "";

        return db.delete("notesPhotos", "id = ?", new String[]{parsedID});
    }

    public void editNote (int id, String color, String title, String noteContent) {
        SQLiteDatabase db = this.getWritableDatabase();

        String parsedID = id + "";

        ContentValues contentValues = new ContentValues();
        contentValues.put("color", color);
        contentValues.put("title", title);
        contentValues.put("noteContent",noteContent );

        db.update("notesPhotos", contentValues, "id = ? ", new String[]{parsedID});
        db.close();

    }


    @SuppressLint("Range")
    public ArrayList<Note> getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Note> notes = new ArrayList<>();
        Cursor result =  db.rawQuery("SELECT * FROM notesPhotos" , null);

        while(result.moveToNext()){
            Log.d("db", result.getColumnCount() + "");
            notes.add( new Note(
                    result.getInt(result.getColumnIndex("id")),
                    result.getString(result.getColumnIndex("color")),
                    result.getString(result.getColumnIndex("title")),
                    result.getString(result.getColumnIndex("noteContent")),
                    result.getString(result.getColumnIndex("photopath"))

            ));
        }
        return notes;
    }
}
