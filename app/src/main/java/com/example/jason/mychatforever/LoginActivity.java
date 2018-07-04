package com.example.jason.mychatforever;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jason.mychatforever.Data.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import static com.example.jason.mychatforever.Adapter.ChatAdapter.TAG_LOCAL_DATA;

public class LoginActivity extends AppCompatActivity {

    EditText etNomorHp;
    Button btLogin, btRegister;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("users");

    SharedPreferences mylocaldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etNomorHp = findViewById(R.id.et_nmr_telpon);
        btLogin = findViewById(R.id.btn_login);
        btRegister = findViewById(R.id.btn_register);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomorHp = etNomorHp.getText().toString();

                if(nomorHp.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Nomor hp tidak boleh Kosong", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child(nomorHp).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mylocaldata = getSharedPreferences(TAG_LOCAL_DATA, MODE_PRIVATE);

                            User user = new User();
                            if(dataSnapshot.exists()){
                                user.setNama(dataSnapshot.child("nama").getValue(String.class));
                                user.setEmail(dataSnapshot.child("email").getValue(String.class));
                                user.setTelepon(dataSnapshot.child("telepon").getValue(String.class));

                                SharedPreferences.Editor editor = mylocaldata.edit();
                                editor.putString("uid", user.getNama());
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("user", user);
                                startActivity(intent);
                                finish();
                            }else {
                                Context context = getApplicationContext();
                                CharSequence text = "User tidak ditemukan";

                                Toast.makeText(context, text,Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}
