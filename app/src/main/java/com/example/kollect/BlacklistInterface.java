package com.example.kollect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BlacklistInterface extends AppCompatActivity {
    private String _USERNAME;
    private long _PREMIUM;
    private Button btnAdd, btnBack;
    private TextView bl_disable;
    private ListView listView;
    private ArrayList<Blacklist> blacklistModelArrayList;
    private CustomAdapterBlacklist customAdapterBlacklist;
    private MySQLiteOpenHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        btnAdd = (Button) findViewById(R.id.Add_Blacklist_btn);
        btnBack = (Button) findViewById(R.id.bl_back);

        databaseHelper = new MySQLiteOpenHelper(this);

        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("user_name");
        _PREMIUM = intent.getLongExtra("premium", 0);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BlacklistInterface.this, Profile.class);
                intent.putExtra("user_name", _USERNAME);
                intent.putExtra("premium",_PREMIUM);
                startActivity(intent);
            }
        });


        if (_PREMIUM == 0){
            btnAdd.setEnabled(false);
            bl_disable = findViewById(R.id.bl_disable);
            bl_disable.setText("You can't add seller since you are not a premium member.");
        }

        listView = (ListView) findViewById(R.id.listview);

        blacklistModelArrayList = databaseHelper.getAllBlacklist();

        customAdapterBlacklist = new CustomAdapterBlacklist(this,blacklistModelArrayList);
        listView.setAdapter(customAdapterBlacklist);

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BlacklistInterface.this, UpdateandDeleteUsers.class);
                intent.putExtra("user", blacklistModelArrayList.get(position));
                startActivity(intent);
            }
        });

         */

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent blacklist = new Intent(getApplicationContext(),AddBlacklist.class);
                blacklist.putExtra("user_name", _USERNAME);
                blacklist.putExtra("premium",_PREMIUM);
                startActivity(blacklist);
            }
        });

    }
}
