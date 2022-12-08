package com.example.kollect;

import static com.example.kollect.Login.USERID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

public class UpdateandDeletePost extends AppCompatActivity {

    private Post post;
    private EditText etsname, etaname, etgroups, etprice,etstatus;
    private Button btnupdate, btndelete;
    private Switch swtch;
    private MySQLiteOpenHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateand_delete_post);

        Intent intent = getIntent();
        post = (Post) intent.getSerializableExtra("user");

        databaseHelper = new MySQLiteOpenHelper(this);

        etsname = (EditText) findViewById(R.id.etsname);
        etaname = (EditText) findViewById(R.id.etaname);
        etgroups = (EditText) findViewById(R.id.etgroups);
        etprice = (EditText) findViewById(R.id.etprice);

        btndelete = (Button) findViewById(R.id.btndelete);
        btnupdate = (Button) findViewById(R.id.btnupdate);
        swtch = (Switch) findViewById(R.id.switch2);

        etsname.setText(post.getSellerName());
        etaname.setText(post.getArtistName());
        etgroups.setText(post.getGroups());
        etprice.setText(String.valueOf(post.getPrice()));
        if ((post.getStatus()) == 1) {
            swtch.setChecked(true);
        } else {
            swtch.setChecked(false);
        }

        swtch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (swtch.isChecked()) {
                    post.setStatus(1);
                }else {
                    post.setStatus(0);
                }
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.updatePost(post.getId(),etsname.getText().toString(),etaname.getText().toString(),etgroups.getText().toString(),Integer.parseInt(etprice.getText().toString()),post.getStatus(),USERID);
                Toast.makeText(UpdateandDeletePost.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateandDeletePost.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.deletePostFromDbByNumber(post.getId());
                Toast.makeText(UpdateandDeletePost.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateandDeletePost.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}