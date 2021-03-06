package com.c3cyberclub.p5_todoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler handler;
    private ListView todo_list;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask();

            }
        });


         handler = new DatabaseHandler(this);
// Get access to the underlying writeable database
         db = handler.getWritableDatabase();
    }

    private void addNewTask() {
        Intent add_new_todo = new Intent(this, NewTask.class);
        startActivity(add_new_todo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

// Query for items from the database and get a cursor back
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);
//        Sorting the data based on the value saved in the settings
        String sort_by=sp.getString("sort_by",DatabaseHandler.KEY_CREATED_AT);

        Cursor todoCursor = db.query(DatabaseHandler.TABLE_TODOS,null,null,null,null,null,sort_by+" ASC ");
        todo_list=(ListView)findViewById(R.id.list_view);
        todo_list.setAdapter(new TodoAdapter(this,todoCursor));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
//          Starting the settings activity when settings menu is clicked
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }
        if(id==R.id.menu_item_share){
            shareText();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void shareText() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBodyText = "i use the best todo app that I made. Wanna try?";
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject/Title");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(intent, "Choose sharing method"));
    }
}
