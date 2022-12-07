package com.example.kollect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Favorite extends AppCompatActivity implements AddFavoriteDialog.AddFavoriteDialogListener {
    private RecyclerView recyclerView1;
    private ArrayList<String> arrayList1;
    private ArrayList<String> arrayList2;
    private RecyclerView recyclerView2;
    private Button addGroup;
    private Button addArtist;
    private MySQLiteOpenHelper databaseHelper;
    private String _USERNAME, _GENDER, _INSTALINK, _PASSWORD,_FAVARTIST,_FAVGROUP;
    DatabaseReference reference;


    //private ArrayList<favartistlist> FavartistlistModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        reference = FirebaseDatabase.getInstance().getReference("Users");
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
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.favorite:
                        startActivity(new Intent(getApplicationContext(),Favorite.class));
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
                        startActivity(changeIfo3);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        Intent changeIfo = new Intent(getApplicationContext(), Profile.class);
                        changeIfo.putExtra("user_name", _USERNAME);
                        changeIfo.putExtra("gender", _GENDER);
                        changeIfo.putExtra("insta_id", _INSTALINK);
                        changeIfo.putExtra("password", _PASSWORD);
                        changeIfo.putExtra("fav_artist", _FAVARTIST);
                        changeIfo.putExtra("fav_group", _FAVGROUP);

                        startActivity(changeIfo);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        readData(_USERNAME);

    }
    private void readData(String _USERNAME){
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(_USERNAME).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String favgroup = String.valueOf(dataSnapshot.child("fav_group").getValue());
                        String s1[] = favgroup.split(";");
                        arrayList1 = new ArrayList<String>(Arrays.asList(s1));
                        String favartist = String.valueOf(dataSnapshot.child("fav_artist").getValue());
                        String s2[]=favartist.split(";");
                        arrayList2 = new ArrayList<String>(Arrays.asList(s2));
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
                    }else{
                        Toast.makeText(Favorite.this,"User doesn't exist",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Favorite.this,"fail to read",Toast.LENGTH_SHORT).show();
                }
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
            reference.child(_USERNAME).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult().exists()){
                            DataSnapshot dataSnapshot = task.getResult();
                            String favgroup = String.valueOf(dataSnapshot.child("fav_group").getValue());
                            favgroup += ";"+groupname;
                            reference.child(_USERNAME).child("fav_group").setValue(favgroup);
                            String s1[] = favgroup.split(";");
                            arrayList1 = new ArrayList<String>(Arrays.asList(s1));
                            recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                            favGroupAdapter myAdapter = new favGroupAdapter(arrayList1);
                            recyclerView1.setAdapter(myAdapter);
                        }else{
                            Toast.makeText(Favorite.this,"User doesn't exist",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Favorite.this,"fail to read",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }else{
            reference.child(_USERNAME).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult().exists()){
                            DataSnapshot dataSnapshot = task.getResult();
                            String favartist = String.valueOf(dataSnapshot.child("fav_artist").getValue());
                            favartist += ";"+groupname;
                            reference.child(_USERNAME).child("fav_artist").setValue(favartist);
                            String s2[]=favartist.split(";");
                            arrayList2 = new ArrayList<String>(Arrays.asList(s2));
                            recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                            favGroupAdapter myAdapter2 = new favGroupAdapter(arrayList2);
                            recyclerView2.setAdapter(myAdapter2);
                        }else{
                            Toast.makeText(Favorite.this,"User doesn't exist",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Favorite.this,"fail to read",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
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