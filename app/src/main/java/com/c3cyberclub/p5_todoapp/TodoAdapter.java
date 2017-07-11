package com.c3cyberclub.p5_todoapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by theseus on 11/7/17.
 */

public class TodoAdapter extends CursorAdapter {
    public TodoAdapter(Context context, Cursor c) {
        super(context, c);
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
