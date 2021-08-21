package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class Musicgroup extends AppCompatActivity {
    ArrayList<Audio> audioList;
    RecyclerView recyclerView;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicgroup);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("Bundle");
        audioList = (ArrayList<Audio>) args.getSerializable("songlist");
      recyclerView = findViewById(R.id.recycleviewa);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new Adapter(this,audioList);
        recyclerView.setAdapter(adapter);
    }
}