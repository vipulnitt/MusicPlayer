package com.example.musicplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class album_fragment extends Fragment {
    RecyclerView recyclerView;
    Adapter2 adapter2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleviewalbum);
        recyclerView.setHasFixedSize(true);
        ArrayList<arrayliststore> arrayList = MainActivity.al;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter2= new Adapter2(getContext(),arrayList,1);
        recyclerView.setAdapter(adapter2);
  return  view;
    }
}
