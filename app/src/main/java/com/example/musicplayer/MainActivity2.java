package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.MyHandler.MyDbHandler;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    Button nextbtn,previousbtn,pausebtn,favbtn;
    TextView sname;
    SeekBar seekBar;
    static MediaPlayer mediaPlayer;
    Thread updateseekbar;
    ArrayList<Audio> audioList;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        nextbtn= findViewById(R.id.nextbtn);
        previousbtn = findViewById(R.id.previousbtn);
        pausebtn =findViewById(R.id.pausebtn);
        sname = findViewById(R.id.songtitle);
        seekBar = findViewById(R.id.seekbar);
        favbtn = findViewById(R.id.fav);
        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        updateseekbar = new Thread(){
            @Override
            public void run() {
                super.run();
                int totalDuration = mediaPlayer.getDuration();
                int currentposition =0;
                while(currentposition<totalDuration)
                {
                    try {
                      sleep(500);
                      currentposition = mediaPlayer.getCurrentPosition();
                      seekBar.setProgress(currentposition);
                    } catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("Bundle");
        audioList = (ArrayList<Audio>) args.getSerializable("songlist");
        String songName = intent.getStringExtra("song");
        sname.setText(songName);
        sname.setSelected(true);
        position = intent.getIntExtra("position",0);
        Uri u = Uri.parse(audioList.get(position).getData());
        mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());
        updateseekbar.start();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
              mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        pausebtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                seekBar.setMax(mediaPlayer.getDuration());
                if(mediaPlayer.isPlaying()) {
                    pausebtn.setBackgroundResource(R.drawable.play);
                    mediaPlayer.pause();
                }
                else
                {
                    pausebtn.setBackgroundResource(R.drawable.pause);
                    mediaPlayer.start();
                }

            }
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position=(position+1)%audioList.size();
                Log.d("vipulll",""+audioList.size()+"  "+position);
                sname.setText(audioList.get(position).getTitle());
                Uri u = Uri.parse(audioList.get(position).getData());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
            }
        });
        previousbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                if((position-1)<0)
                    position=audioList.size()-1;
                else
                    position=position-1;
                Log.d("vipulll",""+audioList.size()+"  "+position);
                sname.setText(audioList.get(position).getTitle());
                Uri u = Uri.parse(audioList.get(position).getData());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
            }
        });
        favbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDbHandler db = new MyDbHandler(MainActivity2.this);
           Audio audio = new Audio();
           audio.setData(audioList.get(position).getData());
           audio.setTitle(audioList.get(position).getTitle());
           audio.setArtist(audioList.get(position).getArtist());
           audio.setArtist(audioList.get(position).getAlbum());
                Toast.makeText(MainActivity2.this, "Added To Favourite", Toast.LENGTH_SHORT).show();
                db.addFav(audio);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}