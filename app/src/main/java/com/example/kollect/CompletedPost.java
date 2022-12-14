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
    private String _USERNAME, _GENDER, _INSTALINK, _PASSWORD,_FAVARTIST,_FAVGROUP;
    private long _PREMIUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_posts);

        Intent intent = getIntent();

        _USERNAME = intent.getStringExtra("user_name");
        _GENDER = intent.getStringExtra("gender");
        _INSTALINK = intent.getStringExtra("insta_id");
        _PASSWORD = intent.getStringExtra("password");
        _FAVARTIST = intent.getStringExtra("fav_artist");
        _FAVGROUP = intent.getStringExtra("fav_group");
        _PREMIUM = intent.getLongExtra("premium", 0);
        if (savedInstanceState != null) {
            _USERNAME = savedInstanceState.getString("user_name");
        }

        listView = (ListView) findViewById(R.id.listview);

        databaseHelper = new MySQLiteOpenHelper(this);

        userModelArrayList = databaseHelper.getAllCompletedPosts();

        customAdapterPosts = new CustomAdapterPosts(this,userModelArrayList);
        listView.setAdapter(customAdapterPosts);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CompletedPost.this, UpdateandDeletePost.class);
                intent.putExtra("user", userModelArrayList.get(position));
                intent.putExtra("user_name", _USERNAME);
                intent.putExtra("gender",_GENDER);
                intent.putExtra("insta_id",_INSTALINK);
                intent.putExtra("password",_PASSWORD);
                intent.putExtra("fav_artist",_FAVARTIST);
                intent.putExtra("fav_group",_FAVGROUP);
                intent.putExtra("premium",_PREMIUM);
                startActivity(intent);
            }
        });

    }
}