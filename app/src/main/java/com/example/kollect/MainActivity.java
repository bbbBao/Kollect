package com.example.kollect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String _USERNAME, _GENDER, _INSTALINK, _PASSWORD,_FAVARTIST,_FAVGROUP;
    private FirebaseDatabase myFirebasedata;
    private ListView listView;
    private ArrayList<Post> postModelArrayList;
    private CustomAdapterPosts customAdapterPosts;
    private MySQLiteOpenHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myFirebasedata = FirebaseDatabase.getInstance();


        Intent intent = getIntent();

        _USERNAME = intent.getStringExtra("user_name");
        _GENDER = intent.getStringExtra("gender");
        _INSTALINK = intent.getStringExtra("insta_id");
        _PASSWORD = intent.getStringExtra("password");
        _FAVARTIST = intent.getStringExtra("fav_artist");
        _FAVGROUP = intent.getStringExtra("fav_group");

        listView = (ListView) findViewById(R.id.listview);

        databaseHelper = new MySQLiteOpenHelper(this);

        postModelArrayList = databaseHelper.getAllPosts();

        customAdapterPosts = new CustomAdapterPosts(this,postModelArrayList);
        listView.setAdapter(customAdapterPosts);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UpdateandDeletePost.class);
                intent.putExtra("user", postModelArrayList.get(position));
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent changeIfo = new Intent(getApplicationContext(), MainActivity.class);
                        changeIfo.putExtra("user_name", _USERNAME);
                        changeIfo.putExtra("gender", _GENDER);
                        changeIfo.putExtra("insta_id", _INSTALINK);
                        changeIfo.putExtra("password", _PASSWORD);
                        changeIfo.putExtra("fav_artist", _FAVARTIST);
                        changeIfo.putExtra("fav_group", _FAVGROUP);


                        startActivity(changeIfo);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favorite:
                        Intent changeIfo1 = new Intent(getApplicationContext(), Favorite.class);
                        changeIfo1.putExtra("user_name", _USERNAME);
                        changeIfo1.putExtra("gender", _GENDER);
                        changeIfo1.putExtra("insta_id", _INSTALINK);
                        changeIfo1.putExtra("password", _PASSWORD);
                        changeIfo1.putExtra("fav_artist", _FAVARTIST);
                        changeIfo1.putExtra("fav_group", _FAVGROUP);


                        startActivity(changeIfo1);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        Intent changeIfo2 = new Intent(getApplicationContext(), Search.class);
                        changeIfo2.putExtra("user_name", _USERNAME);
                        changeIfo2.putExtra("gender", _GENDER);
                        changeIfo2.putExtra("insta_id", _INSTALINK);
                        changeIfo2.putExtra("password", _PASSWORD);
                        changeIfo2.putExtra("fav_artist", _FAVARTIST);
                        changeIfo2.putExtra("fav_group", _FAVGROUP);


                        startActivity(changeIfo2);
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        Intent changeIfo3 = new Intent(getApplicationContext(), Profile.class);
                        changeIfo3.putExtra("user_name", _USERNAME);
                        changeIfo3.putExtra("gender", _GENDER);
                        changeIfo3.putExtra("insta_id", _INSTALINK);
                        changeIfo3.putExtra("password", _PASSWORD);
                        changeIfo3.putExtra("fav_artist", _FAVARTIST);
                        changeIfo3.putExtra("fav_group", _FAVGROUP);


                        startActivity(changeIfo3);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }


    public void newPostActivity(View v) {
        Intent i = new Intent(MainActivity.this, AddPost.class);
        startActivity(i);
    }

    public void newUserActivity(View v) {
        Intent i = new Intent(MainActivity.this, AddUser.class);
        startActivity(i);
    }

}