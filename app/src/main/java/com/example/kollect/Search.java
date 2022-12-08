package com.example.kollect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Search extends AppCompatActivity {

    private String _USERNAME, _GENDER, _INSTALINK, _PASSWORD,_FAVARTIST,_FAVGROUP;
    private Button search_group_btn;
    private EditText search_group;
    private Button search_artist_btn;
    private EditText search_artist;
    private EditText search_blacklist;
    private Button search_blacklist_btn;
    private String artist_name;
    private String group_name;
    private String blacklist_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_group_btn = (Button)findViewById(R.id.search_group_btn);
        search_group = (EditText)findViewById(R.id.search_group);
        search_artist_btn = (Button)findViewById(R.id.search_artist_btn);
        search_artist = (EditText)findViewById(R.id.search_artist);
        search_blacklist_btn = (Button)findViewById(R.id.search_blacklist_btn);
        search_blacklist = (EditText)findViewById(R.id.search_blacklist);

        Intent intent = getIntent();

        _USERNAME = intent.getStringExtra("user_name");
        _GENDER = intent.getStringExtra("gender");
        _INSTALINK = intent.getStringExtra("insta_id");
        _PASSWORD = intent.getStringExtra("password");
        _FAVARTIST = intent.getStringExtra("fav_artist");
        _FAVGROUP = intent.getStringExtra("fav_group");
        if (savedInstanceState != null) {
            _USERNAME = savedInstanceState.getString("user_name");

        }
        search_group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group_name = search_group.getText().toString();
                Intent intent = new Intent(Search.this, SearchResult.class);
                intent.putExtra("g_name",group_name);
                startActivity(intent);

            }

        });
        search_artist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                artist_name = search_artist.getText().toString();
                Intent intent = new Intent(Search.this, SearchResult.class);
                intent.putExtra("a_name",artist_name);
                startActivity(intent);

            }

        });
        search_blacklist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blacklist_name = search_blacklist.getText().toString();
                Intent intent = new Intent(Search.this, SearchResult.class);
                intent.putExtra("b_name",blacklist_name);
                startActivity(intent);

            }

        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
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
                        Intent changeIfo1 = new Intent(getApplicationContext(),Favorite.class);
                        changeIfo1.putExtra("user_name",_USERNAME);
                        changeIfo1.putExtra("gender",_GENDER);
                        changeIfo1.putExtra("insta_id",_INSTALINK);
                        changeIfo1.putExtra("password",_PASSWORD);
                        changeIfo1.putExtra("fav_artist",_FAVARTIST);
                        changeIfo1.putExtra("fav_group",_FAVGROUP);
                        startActivity(changeIfo1);
                        overridePendingTransition(0,0);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("user_name", _USERNAME);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        _USERNAME = savedInstanceState.getString("user_name");
    }
}