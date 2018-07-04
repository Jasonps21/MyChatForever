package com.example.jason.mychatforever;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.jason.mychatforever.Adapter.ChatAdapter;
import com.example.jason.mychatforever.Data.Chat;
import com.example.jason.mychatforever.Data.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.jason.mychatforever.Adapter.ChatAdapter.TAG_LOCAL_DATA;

public class MainActivity extends AppCompatActivity {

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference databaseReference = database.getReference("chats");

    DatabaseReference root;
    ArrayList<Chat> chats = new ArrayList<>();

    EditText etMessage;
    ImageButton btnSend;

    RecyclerView rvChat;
    ChatAdapter chatAdaptet;

    String temp_key;
    User user;
    SharedPreferences myLocalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        root = FirebaseDatabase.getInstance().getReference().child("chats");

        myLocalData = getSharedPreferences(TAG_LOCAL_DATA, MODE_PRIVATE);
        user = getIntent().getParcelableExtra("user");

        if (user == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chats.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Chat chat = postSnapshot.getValue(Chat.class);
                    chats.add(chat);
                    chatAdaptet.notifyDataSetChanged();
                }
                rvChat.post(new Runnable() {
                    @Override
                    public void run() {
                        rvChat.smoothScrollToPosition(chatAdaptet.getItemCount()-1);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rvChat = findViewById(R.id.reyclerview_message_list);
        rvChat.setHasFixedSize(true);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        chatAdaptet = new ChatAdapter(this, chats);
        rvChat.setAdapter(chatAdaptet);

        etMessage = findViewById(R.id.edittext_chatbox);
        btnSend = findViewById(R.id.button_chatbox_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(etMessage.getText())){
                    return;
                }
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_root = root.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("sender", user.getNama());
                map2.put("pesan", etMessage.getText().toString());
                map2.put("tanggal", new Date().getTime());
                message_root.updateChildren(map2);
                etMessage.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuLogout:
                finish();
                return true;
            case R.id.menuDetail:
                Intent intent = new Intent(MainActivity.this, DetailUser.class);
                intent.putExtra("user", user);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
