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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.units.qual.A;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Premium extends AppCompatActivity {
    private String _USERNAME;
    private long _PREMIUM;
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
    private TextView disable;
    private Button back;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        artist_name = (EditText) findViewById(R.id.artist_name);
        search_btn = (Button) findViewById(R.id.search_btn);
        lower_bound = (TextView) findViewById(R.id.lower_bound);
        upper_bound = (TextView) findViewById(R.id.upper_bound);
        mean = (TextView) findViewById(R.id.mean);
        median = (TextView) findViewById(R.id.median);
        predict = (TextView) findViewById(R.id.predict);
        back = (Button) findViewById(R.id.button_back);

        Intent intent = getIntent();

        _USERNAME = intent.getStringExtra("user_name");
        _PREMIUM = intent.getLongExtra("premium", 0);

        databaseHelper = new MySQLiteOpenHelper(this);

        boolean isPremium = databaseHelper.getPremium(_USERNAME);
        if (_PREMIUM == 0){
            search_btn.setEnabled(false);
            disable = findViewById(R.id.tv_disable);
            disable.setText("Premium member only. Function has been disabled.");
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Premium.this, Profile.class);
                intent.putExtra("user_name", _USERNAME);
                intent.putExtra("premium", _PREMIUM);
                startActivity(intent);
            }
        });

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