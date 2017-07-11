package com.c3cyberclub.p5_todoapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

    }


    public void addTodo(String name, String deadline,String created_at){
        ContentValues cv=new ContentValues();
        cv.put(DatabaseHandler.KEY_NAME, name);
        cv.put(DatabaseHandler.KEY_DEADLINE, deadline);
        cv.put(DatabaseHandler.KEY_CREATED_AT, created_at);
        SQLiteDatabase db = new DatabaseHandler(this).getWritableDatabase();
        db.insert(DatabaseHandler.TABLE_TODOS,null,cv);
        finish();
    }
}
