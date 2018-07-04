package com.example.jason.mychatforever;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jason.mychatforever.Data.User;

public class DetailUser extends AppCompatActivity {

    User user;
    TextView tv_nama,tv_email, tv_nomor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        user = getIntent().getParcelableExtra("user");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_nama = findViewById(R.id.tv_nama);
        tv_email = findViewById(R.id.tv_email);
        tv_nomor = findViewById(R.id.tv_nomor);

        tv_nama.setText(user.getNama());
        tv_nomor.setText(user.getTelepon());
        tv_email.setText(user.getEmail());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
