package com.example.kollect;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class getAllPosts extends AppCompatActivity {

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

        userModelArrayList = databaseHelper.getAllPosts();

        customAdapterPosts = new CustomAdapterPosts(this,userModelArrayList);
        listView.setAdapter(customAdapterPosts);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getAllPosts.this, UpdateandDeletePost.class);
                intent.putExtra("user", userModelArrayList.get(position));
                startActivity(intent);
            }
        });

    }
}