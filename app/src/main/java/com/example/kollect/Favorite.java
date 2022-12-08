package com.example.kollect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
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
import java.util.Locale;

public class Favorite extends AppCompatActivity implements RecyclerViewInterface,RecyclerViewInterface2, AddFavoriteDialog.AddFavoriteDialogListener, AddFavoriteArtistDialog.AddFavoriteArtistDialogListener {
    private RecyclerView recyclerView1;
    private ArrayList<String> arrayList1;
    private ArrayList<String> arrayList2;
    private RecyclerView recyclerView2;
    private String group_name;
    private String artist_name;
    private TextView nogroup;
    private TextView noartist;
    private Button addGroup;
    private Button addArtist;
    private MySQLiteOpenHelper databaseHelper;
    private String _USERNAME, _GENDER, _INSTALINK, _PASSWORD,_FAVARTIST,_FAVGROUP;
    private favArtistAdapter adapter1;
    private favGroupAdapter adapter2;
    DatabaseReference reference;
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
        nogroup = findViewById(R.id.tv_no_data);
        noartist = findViewById(R.id.tv_no_data2);
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
                        if(arrayList1.isEmpty()){
                            nogroup.setVisibility(View.VISIBLE);
                        }else{
                            recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                            favGroupAdapter myAdapter = new favGroupAdapter(arrayList1, Favorite.this);
                            recyclerView1.setAdapter(myAdapter);
                        }
                        String favartist = String.valueOf(dataSnapshot.child("fav_artist").getValue());
                        String s2[] = favartist.split(";");
                        arrayList2 = new ArrayList<String>(Arrays.asList(s2));
                        if(arrayList2.isEmpty()){
                            noartist.setVisibility(View.VISIBLE);
                        }else{
                        recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                        favArtistAdapter myAdapter2 = new favArtistAdapter(arrayList2, Favorite.this);
                        recyclerView2.setAdapter(myAdapter2);
                        addGroup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openDialog1();
                            }
                        });
                        addArtist.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                openDialog2();
                            }
                        });
                        }

                    }else{
                        Toast.makeText(Favorite.this,"User doesn't exist",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Favorite.this,"fail to read",Toast.LENGTH_SHORT).show();
                }
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.favorite);
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
                        startActivity(changeIfo);
                        overridePendingTransition(0,0);
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
    public void openDialog1(){
        AddFavoriteDialog addFavoriteDialog = new AddFavoriteDialog();
        addFavoriteDialog.show(getSupportFragmentManager(),"example group dialog");
    }
    public void openDialog2(){
        AddFavoriteArtistDialog addFavoriteArtistDialog = new AddFavoriteArtistDialog();
        addFavoriteArtistDialog.show(getSupportFragmentManager(),"example artist dialog");
    }
    @Override
    public void applyTexts1(String groupname) {
            reference.child(_USERNAME).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult().exists()){
                            DataSnapshot dataSnapshot = task.getResult();
                            String favgroup = String.valueOf(dataSnapshot.child("fav_group").getValue());
                            if(favgroup.equals("")){favgroup+=groupname;}else{
                                favgroup +=";"+groupname;}
                            reference.child(_USERNAME).child("fav_group").setValue(favgroup);
                            String s1[] = favgroup.split(";");
                            arrayList1 = new ArrayList<String>(Arrays.asList(s1));
                            recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                            favGroupAdapter myAdapter = new favGroupAdapter(arrayList1,Favorite.this);
                            recyclerView1.setAdapter(myAdapter);
                        }else{
                            Toast.makeText(Favorite.this,"User doesn't exist",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Favorite.this,"fail to read",Toast.LENGTH_SHORT).show();
                    }
                }
            });}
        @Override
        public void applyTexts2(String groupname) {
            reference.child(_USERNAME).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult().exists()){
                            DataSnapshot dataSnapshot = task.getResult();
                            String favartist = String.valueOf(dataSnapshot.child("fav_artist").getValue());
                            if(favartist.equals("")){favartist+=groupname;}else{
                            favartist +=";"+groupname;}
                            reference.child(_USERNAME).child("fav_artist").setValue(favartist);
                            String s2[]=favartist.split(";");
                            arrayList2 = new ArrayList<String>(Arrays.asList(s2));
                            recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                            favArtistAdapter myAdapter2 = new favArtistAdapter(arrayList2,Favorite.this);
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

    @Override
    public void onItemClick1(int position) {
        group_name = arrayList1.get(position);
        Intent intent = new Intent(Favorite.this,FavoriteResult.class);
        intent.putExtra("g_name",group_name);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick1(int position) {
        reference.child(_USERNAME).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String favgroup = String.valueOf(dataSnapshot.child("fav_group").getValue());
                        String s1[] = favgroup.split(";");
                        arrayList1 = new ArrayList<String>(Arrays.asList(s1));
                        arrayList1.remove(position);
                        String str1 = TextUtils.join(";",arrayList1);
                        reference.child(_USERNAME).child("fav_group").setValue(str1);
                        recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                        favGroupAdapter myAdapter = new favGroupAdapter(arrayList1,Favorite.this);
                        myAdapter.notifyItemRemoved(position);
                        recyclerView1.setAdapter(myAdapter);
                    }else{
                        Toast.makeText(Favorite.this,"User doesn't exist",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Favorite.this,"fail to read",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemClick2(int position) {
        artist_name = arrayList2.get(position);
        Intent intent = new Intent(Favorite.this,FavoriteResult.class);
        intent.putExtra("a_name",artist_name);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick2(int position) {
        reference.child(_USERNAME).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            if(task.isSuccessful()){
                if(task.getResult().exists()){
                    DataSnapshot dataSnapshot = task.getResult();
                    String favartist = String.valueOf(dataSnapshot.child("fav_artist").getValue());
                    String s2[]=favartist.split(";");
                    arrayList2 = new ArrayList<String>(Arrays.asList(s2));
                    arrayList2.remove(position);
                    String str2 = TextUtils.join(";",arrayList2);
                    reference.child(_USERNAME).child("fav_artist").setValue(str2);
                    recyclerView2.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                    favArtistAdapter myAdapter2 = new favArtistAdapter(arrayList2,Favorite.this);
                    myAdapter2.notifyItemRemoved(position);
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