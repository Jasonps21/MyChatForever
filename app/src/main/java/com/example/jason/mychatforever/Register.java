package com.example.jason.mychatforever;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jason.mychatforever.Data.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText etNama, etEmail, etTelpon;
    Button btn_register;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userRef = database.getReference().child("users");

        btn_register = findViewById(R.id.btn_register);
        etNama = findViewById(R.id.et_nama);
        etEmail = findViewById(R.id.et_email);
        etTelpon = findViewById(R.id.et_nmr_telpon);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama, email, telpon;

                nama = etNama.getText().toString();
                email = etEmail.getText().toString();
                telpon = etTelpon.getText().toString();

                if (TextUtils.isEmpty(nama)) {
                    Toast.makeText(getApplicationContext(), "Masukkan nama anda", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (telpon.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Nomor Telpon Terlalu pendek", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Masukkan Email Anda", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setEmail(email);
                user.setNama(nama);
                user.setTelepon(telpon);

                userRef.child(telpon).setValue(user);
                Toast.makeText(getApplicationContext(), "Data telah tersimpan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}