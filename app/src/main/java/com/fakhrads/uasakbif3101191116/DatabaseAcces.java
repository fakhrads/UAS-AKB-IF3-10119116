/**
 * NAMA : FAKHRI ADI SAPUTRA
 * NIM : 10119116
 * KELAS : IF-3
 */
package com.fakhrads.uasakbif3101191116;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseAcces {
    private Context context;
    private SQLiteDatabase database;
    private DatabaseOpenHelper openHelper;
    private static volatile DatabaseAcces instance;

    private DatabaseAcces(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static synchronized DatabaseAcces getInstance(Context context){
        if (instance == null){
            instance = new DatabaseAcces(context);
        }
        return instance;
    }

    public void open(){
        this.database = openHelper.getWritableDatabase();
    }

    public void close(){
        if (database != null){
            this.database.close();
        }
    }

    public void save(Notes memo){
        ContentValues values = new ContentValues();
        values.put("date", memo.getTime());
        values.put("memo", memo.getText());
        values.put("title", memo.getTtitle());
        values.put("category", memo.getCategory());
        long result = database.insert(DatabaseOpenHelper.TABLE,null,values);
        if (result == -1) {
            Log.e("ERROR:","Gagal");
        } else {
            Log.i("SUCCESS:","Success")  ;
        }
    }

    public void update(Notes memo){
        ContentValues values = new ContentValues();
        values.put("date", new Date().getTime());
        values.put("memo", memo.getText());
        values.put("title", memo.getTtitle());
        values.put("category", memo.getCategory());
        String date = Long.toString(memo.getTime());
        database.update(DatabaseOpenHelper.TABLE,values, "date = ?", new String[]{date});

    }

    public void delete(Notes memo){
        String date = Long.toString(memo.getTime());
        database.delete(DatabaseOpenHelper.TABLE, "date = ?", new String[]{date});
    }

    public List getAllMemos(){
        List memos = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT date, memo, title, category  FROM memo ORDER BY date DESC", null);
        Log.i("TAG", String.valueOf(cursor.getPosition()));
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            long time = cursor.getLong(0);
            String text = cursor.getString(1);
            String title = cursor.getString(2);
            String category = cursor.getString(3);
            memos.add(new Notes(time, text, title, category));
            cursor.moveToNext();
        }

        cursor.close();
        return memos;
    }
}
