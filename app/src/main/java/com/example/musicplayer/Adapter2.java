package com.example.musicplayer;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;


public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {
    LayoutInflater inflater;
    List<arrayliststore> data;
    int check;
    Context context;
    public Adapter2(Context ctx, List<arrayliststore>  data,int check){
        this.inflater =LayoutInflater.from(ctx);
        this.data =data;
        this.context=ctx;
        this.check=check;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.custom_list_layout,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(data.get(position).getArtist());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Musicgroup.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                Bundle args = new Bundle();
                if(check==0)
                args.putSerializable("songlist", (Serializable) data.get(position).getArrayList());
                else
                    args.putSerializable("songlist", (Serializable) data.get(position).getArrayListal());
                intent.putExtra("Bundle",args);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
       View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            this.view = itemView;
        }
    }
 }
