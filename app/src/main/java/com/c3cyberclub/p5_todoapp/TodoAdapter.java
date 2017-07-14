package com.c3cyberclub.p5_todoapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by theseus on 11/7/17.
 */

public class TodoAdapter extends CursorAdapter {
    private DatabaseHandler handler;
    private SQLiteDatabase db;
    private Context mContext;
    public TodoAdapter(Context context, Cursor c) {
        super(context, c);
        mContext=context;
//        Getting access to the database to delete the item
        handler = new DatabaseHandler(mContext);
        db = handler.getWritableDatabase();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title=(TextView)view.findViewById(R.id.textTitle);
        title.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_NAME)));

        TextView duedate=(TextView)view.findViewById(R.id.textDueDate);
        duedate.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHandler.KEY_DEADLINE)));
    }
}
