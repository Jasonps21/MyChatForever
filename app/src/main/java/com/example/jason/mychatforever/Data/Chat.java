package com.example.jason.mychatforever.Data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Chat {
    private String sender;
    private String pesan;
    private Long tanggal;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference chatRef = database.getReference("chats");

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public Long getTanggal() {
        return tanggal;
    }

    public void setTanggal(Long tanggal) {
        this.tanggal = tanggal;
    }
}
