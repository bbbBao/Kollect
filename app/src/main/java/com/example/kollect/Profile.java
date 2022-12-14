package com.example.kollect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {
    private TextView change_info_txt;
    private ImageView change_info_arr;
    private TextView username;
    private TextView completed_purchase_txt;
    private ImageView completed_purchase_arr;
    private TextView pre_txt;
    private ImageView pre_arr;
    private TextView get_pre_txt;
    private ImageView get_pre_arr;
    private TextView blacklist_txt;
    private ImageView blacklist_arr;
    private TextView logout_txt;
    private ImageView logout_arr;
    private Button instaButton;
    private String _USERNAME,_GENDER,_INSTALINK,_PASSWORD,_FAVARTIST,_FAVGROUP;
    private long _PREMIUM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        change_info_txt = (TextView)findViewById(R.id.change_info_txt);
        change_info_arr = (ImageView)findViewById(R.id.change_info_arr);
        username = (TextView) findViewById(R.id.username);
        completed_purchase_txt = (TextView)findViewById(R.id.completed_purchase_txt);
        completed_purchase_arr = (ImageView)findViewById(R.id.completed_purchase_arr);
        pre_txt = (TextView)findViewById(R.id.pre_txt);
        pre_arr = (ImageView)findViewById(R.id.pre_arr);
        get_pre_txt = (TextView)findViewById(R.id.get_pre_txt);
        get_pre_arr = (ImageView)findViewById(R.id.get_pre_arr);
        blacklist_txt = (TextView)findViewById(R.id.blacklist_txt);
        blacklist_arr = (ImageView)findViewById(R.id.blacklist_arr);
        logout_txt = (TextView)findViewById(R.id.logout_txt);
        logout_arr = (ImageView)findViewById(R.id.logout_arr);

        Intent intent = getIntent();

        _USERNAME = intent.getStringExtra("user_name");
        _GENDER = intent.getStringExtra("gender");
        _INSTALINK = intent.getStringExtra("insta_id");
        _PASSWORD = intent.getStringExtra("password");
        _FAVARTIST = intent.getStringExtra("fav_artist");
        _FAVGROUP = intent.getStringExtra("fav_group");
        _PREMIUM = intent.getLongExtra("premium",0);

        if (savedInstanceState != null) {
            _USERNAME = savedInstanceState.getString("user_name");

        }
        username.setText(_USERNAME);
        change_info_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent changeIfo = new Intent(getApplicationContext(),UserProfileUpdate.class);
                changeIfo.putExtra("user_name",_USERNAME);
                changeIfo.putExtra("gender",_GENDER);
                changeIfo.putExtra("insta_id",_INSTALINK);
                changeIfo.putExtra("password",_PASSWORD);
                changeIfo.putExtra("fav_artist",_FAVARTIST);
                changeIfo.putExtra("fav_group",_FAVGROUP);
                changeIfo.putExtra("premium",_PREMIUM);
                startActivity(changeIfo);
            }
        });
        change_info_arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent changeIfo = new Intent(getApplicationContext(),UserProfileUpdate.class);
                changeIfo.putExtra("user_name",_USERNAME);
                changeIfo.putExtra("gender",_GENDER);
                changeIfo.putExtra("insta_id",_INSTALINK);
                changeIfo.putExtra("password",_PASSWORD);
                changeIfo.putExtra("fav_artist",_FAVARTIST);
                changeIfo.putExtra("fav_group",_FAVGROUP);
                changeIfo.putExtra("premium",_PREMIUM);

                startActivity(changeIfo);
            }
        });

        blacklist_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent blacklist = new Intent(getApplicationContext(),BlacklistInterface.class);
                blacklist.putExtra("user_name", _USERNAME);
                blacklist.putExtra("premium",_PREMIUM);
                startActivity(blacklist);
            }
        });
        blacklist_arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent blacklist = new Intent(getApplicationContext(),BlacklistInterface.class);
                blacklist.putExtra("user_name", _USERNAME);
                blacklist.putExtra("premium",_PREMIUM);
                startActivity(blacklist);
            }
        });

        get_pre_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getPremium = new Intent(getApplicationContext(),GetPremium.class);
                getPremium.putExtra("user_name", _USERNAME);
                getPremium.putExtra("premium",_PREMIUM);
                startActivity(getPremium);
            }
        });
        get_pre_arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getPremium = new Intent(getApplicationContext(),GetPremium.class);
                getPremium.putExtra("user_name", _USERNAME);
                getPremium.putExtra("premium",_PREMIUM);
                startActivity(getPremium);
            }
        });


        pre_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Premium = new Intent(getApplicationContext(), com.example.kollect.Premium.class);
                Premium.putExtra("user_name", _USERNAME);
                Premium.putExtra("premium",_PREMIUM);
                startActivity(Premium);
            }
        });

        pre_arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Premium = new Intent(getApplicationContext(), com.example.kollect.Premium.class);
                Premium.putExtra("user_name", _USERNAME);
                Premium.putExtra("premium",_PREMIUM);
                startActivity(Premium);
            }
        });

        completed_purchase_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CompletedPost = new Intent(getApplicationContext(), com.example.kollect.CompletedPost.class);
                CompletedPost.putExtra("user_name", _USERNAME);
                CompletedPost.putExtra("premium",_PREMIUM);
                startActivity(CompletedPost);
            }
        });

        completed_purchase_arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CompletedPost = new Intent(getApplicationContext(), com.example.kollect.CompletedPost.class);
                CompletedPost.putExtra("user_name", _USERNAME);
                CompletedPost.putExtra("premium",_PREMIUM);
                startActivity(CompletedPost);
            }
        });

        logout_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Logout = new Intent(getApplicationContext(), com.example.kollect.Login.class);
                startActivity(Logout);
            }
        });

        logout_arr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Logout = new Intent(getApplicationContext(), com.example.kollect.Login.class);
                startActivity(Logout);
            }
        });

        instaButton = findViewById(R.id.instagram_btn);
        instaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Uri uri = Uri.parse("http://instagram.com/_u/"+_INSTALINK);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            uri));
                }
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent changeIfo = new Intent(getApplicationContext(),MainActivity.class);
                        changeIfo.putExtra("user_name",_USERNAME);
                        changeIfo.putExtra("gender",_GENDER);
                        changeIfo.putExtra("insta_id",_INSTALINK);
                        changeIfo.putExtra("password",_PASSWORD);
                        changeIfo.putExtra("fav_artist",_FAVARTIST);
                        changeIfo.putExtra("fav_group",_FAVGROUP);
                        changeIfo.putExtra("premium",_PREMIUM);
                        startActivity(changeIfo);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.favorite:
                        Intent changeIfo2 = new Intent(getApplicationContext(),Favorite.class);
                        changeIfo2.putExtra("user_name",_USERNAME);
                        changeIfo2.putExtra("gender",_GENDER);
                        changeIfo2.putExtra("insta_id",_INSTALINK);
                        changeIfo2.putExtra("password",_PASSWORD);
                        changeIfo2.putExtra("fav_artist",_FAVARTIST);
                        changeIfo2.putExtra("fav_group",_FAVGROUP);
                        changeIfo2.putExtra("premium",_PREMIUM);
                        startActivity(changeIfo2);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        Intent changeIfo3 = new Intent(getApplicationContext(),Search.class);
                        changeIfo3.putExtra("user_name",_USERNAME);
                        changeIfo3.putExtra("gender",_GENDER);
                        changeIfo3.putExtra("insta_id",_INSTALINK);
                        changeIfo3.putExtra("password",_PASSWORD);
                        changeIfo3.putExtra("fav_artist",_FAVARTIST);
                        changeIfo3.putExtra("fav_group",_FAVGROUP);
                        changeIfo3.putExtra("premium",_PREMIUM);
                        startActivity(changeIfo3);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        Intent changeIfo4 = new Intent(getApplicationContext(),Profile.class);
                        changeIfo4.putExtra("user_name",_USERNAME);
                        changeIfo4.putExtra("gender",_GENDER);
                        changeIfo4.putExtra("insta_id",_INSTALINK);
                        changeIfo4.putExtra("password",_PASSWORD);
                        changeIfo4.putExtra("fav_artist",_FAVARTIST);
                        changeIfo4.putExtra("fav_group",_FAVGROUP);
                        changeIfo4.putExtra("premium",_PREMIUM);
                        startActivity(changeIfo4);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("user_name", username.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        _USERNAME = savedInstanceState.getString("user_name");
    }

}