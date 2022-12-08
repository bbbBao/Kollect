package com.example.kollect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CompletedPost extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Post> userModelArrayList;
    private CustomAdapterPosts customAdapterPosts;
    private MySQLiteOpenHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_posts);

        listView = (ListView) findViewById(R.id.listview);

        databaseHelper = new MySQLiteOpenHelper(this);

        userModelArrayList = databaseHelper.getAllCompletedPosts();

        customAdapterPosts = new CustomAdapterPosts(this,userModelArrayList);
        listView.setAdapter(customAdapterPosts);



    }
}