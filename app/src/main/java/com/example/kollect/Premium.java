package com.example.kollect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.checkerframework.checker.units.qual.A;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Premium extends AppCompatActivity {
    private String _USERNAME, _GENDER, _INSTALINK, _PASSWORD, _FAVARTIST, _FAVGROUP;
    private EditText artist_name;
    private Button search_btn;
    private TextView lower_bound;
    private TextView upper_bound;
    private TextView mean;
    private TextView median;
    private TextView predict;
    private CustomAdapterPosts customAdapterPosts;
    private MySQLiteOpenHelper databaseHelper;
    private ArrayList<Integer> PriceArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        artist_name = (EditText) findViewById(R.id.artist_name);
        search_btn = (Button) findViewById(R.id.search_btn);
        lower_bound = (TextView) findViewById(R.id.lower_bound);
        upper_bound = (TextView) findViewById(R.id.upper_bound);
        mean = (TextView) findViewById(R.id.mean);
        median = (TextView) findViewById(R.id.median);
        predict = (TextView) findViewById(R.id.predict);

        Intent intent = getIntent();

        _USERNAME = intent.getStringExtra("user_name");
        _GENDER = intent.getStringExtra("gender");
        _INSTALINK = intent.getStringExtra("insta_id");
        _PASSWORD = intent.getStringExtra("password");
        _FAVARTIST = intent.getStringExtra("fav_artist");
        _FAVGROUP = intent.getStringExtra("fav_group");

        databaseHelper = new MySQLiteOpenHelper(this);


        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a_name = artist_name.getText().toString();
                Log.w(a_name, "artist name");

                  PriceArrayList = databaseHelper.getPrice(a_name);

                if (PriceArrayList.size()!=0){
                    lower_bound.setText(Integer.toString(getLowerBound(PriceArrayList)));
                    upper_bound.setText(Integer.toString(getUpperBound(PriceArrayList)));
                    mean.setText(String.valueOf(getMean(PriceArrayList)));
                    median.setText(String.valueOf(getLowerBound(PriceArrayList)));
                    double sd = getSD(PriceArrayList);
                    String format = new DecimalFormat("##.##").format(sd);
                    predict.setText(String.valueOf(getMean(PriceArrayList)) + "±" + format);
                }else{
                    Toast.makeText(Premium.this, "No such data found", Toast.LENGTH_LONG).show();
                }





            }
        });



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favorite:
                        startActivity(new Intent(getApplicationContext(), Favorite.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0, 0);
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
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
    }

    //    lower bound
    public Integer getLowerBound(ArrayList<Integer> arr) {

        return Collections.min(arr);
    }

    //    upper bound
    public Integer getUpperBound(ArrayList<Integer> arr) {

        return Collections.max(arr);
    }

    //    mean
    public double getMean(ArrayList<Integer> arr) {

        Integer sum = 0;
        if (!arr.isEmpty()) {
            for (Integer element : arr) {
                sum += element;
            }
            return sum.doubleValue() / arr.size();
        }
        return sum;

    }

    public double getMedian(ArrayList<Integer> arr){
        Collections.sort(arr);
        int middle = arr.size() / 2;
        middle = middle > 0 && middle % 2 == 0 ? middle - 1 : middle;
        return arr.get(middle);

    }

//    get standard deviation

    public double getSD(ArrayList<Integer> numArray)
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.size();

        for(double num : numArray) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }
}