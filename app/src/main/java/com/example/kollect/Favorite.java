package com.example.kollect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Favorite extends AppCompatActivity implements AddFavoriteDialog.AddFavoriteDialogListener {
    private RecyclerView recyclerView1;
    private ArrayList<String> arrayList1;
    private ArrayList<String> arrayList2;
    private RecyclerView recyclerView2;
    private Button addGroup;
    private Button addArtist;
    private MySQLiteOpenHelper databaseHelper;
    private String _USERNAME, _GENDER, _INSTALINK, _PASSWORD,_FAVARTIST,_FAVGROUP;


    //private ArrayList<favartistlist> FavartistlistModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView1 = findViewById(R.id.groups_added);
        recyclerView2 = findViewById(R.id.artists_added);
        addGroup = findViewById(R.id.addGroupBtn);
        addArtist = findViewById(R.id.addArtistBtn);
        databaseHelper = new MySQLiteOpenHelper(this);
        // favoritelistModelArrayList = databaseHelper.getall;
        Intent intent = getIntent();

        _USERNAME = intent.getStringExtra("user_name");
        _GENDER = intent.getStringExtra("gender");
        _INSTALINK = intent.getStringExtra("insta_id");
        _PASSWORD = intent.getStringExtra("password");
        _FAVARTIST = intent.getStringExtra("fav_artist");
        _FAVGROUP = intent.getStringExtra("fav_group");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.favorite);
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
        arrayList1 = new ArrayList<String>();
        arrayList2 = new ArrayList<String>();
        arrayList1.add("BTS");
        arrayList1.add("BlackPink");
        arrayList2.add("Jennie");
        arrayList2.add("Lisa");
        arrayList2.add("Jimin");
        // arrayList2.add("bbb");
        recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        favGroupAdapter myAdapter = new favGroupAdapter(arrayList1);
        recyclerView1.setAdapter(myAdapter);

        recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        favGroupAdapter myAdapter2 = new favGroupAdapter(arrayList2);
        recyclerView2.setAdapter(myAdapter2);

        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

        });
        addArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    public void openDialog(){
        AddFavoriteDialog addFavoriteDialog = new AddFavoriteDialog();
        addFavoriteDialog.show(getSupportFragmentManager(),"example dialog");
    }
    @Override
    public void applyTexts(String groupname, Switch what) {
        if(!what.isChecked()){
            arrayList1.add(groupname);
        }else{
            arrayList2.add(groupname);
        }
    }

}