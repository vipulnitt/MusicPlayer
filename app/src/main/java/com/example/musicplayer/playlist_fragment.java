package com.example.musicplayer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.MyHandler.MyDbHandler;

import java.util.ArrayList;
import java.util.List;

public class playlist_fragment extends Fragment {
    RecyclerView recyclerView;
    Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleviewfav);
       MyDbHandler db = new MyDbHandler(getContext());
       List<Audio> favdata = db.getalldata();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       adapter= new Adapter(getContext(),favdata);
       recyclerView.setAdapter(adapter);
        return view;
    }
}
