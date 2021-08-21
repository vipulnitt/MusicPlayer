package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Audio> audioList;
    public static ArrayList<arrayliststore> ar;
    public static ArrayList<arrayliststore> al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
 permission();
}
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_music:
                            selectedFragment = new Music_fragment();
                            break;
                        case R.id.nav_playlist:
                            selectedFragment = new playlist_fragment();
                            break;
                        case R.id.nav_albums:
                            selectedFragment = new album_fragment();
                            break;
                        case R.id.nav_artist:
                            selectedFragment = new artist_fragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
    public void permission()
    {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        loadAudio();
                       getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                              new Music_fragment()).commit();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

     @RequiresApi(api = Build.VERSION_CODES.N)
     public void loadAudio() {
      ContentResolver contentResolver = getContentResolver();
      Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
      String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
      String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
      Cursor cursor = contentResolver.query(uri, null, selection, null, sortOrder);

      if (cursor != null && cursor.getCount() > 0) {
          audioList = new ArrayList<>();
          while (cursor.moveToNext()) {
              String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
              String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
              String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
              String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
              audioList.add(new Audio(data, title, album, artist));
          }
      }
      cursor.close();
      ar = new ArrayList<>();
         Map<String, List<Audio>> artist
                 = audioList.stream().collect(Collectors.groupingBy(Audio::getArtist));
         for (Map.Entry<String, List<Audio>> entry: artist.entrySet()) {
             {
                 ar.add(new arrayliststore(entry.getKey()));
             }
         }
         al = new ArrayList<>();
         Map<String, List<Audio>> album
                 = audioList.stream().collect(Collectors.groupingBy(Audio::getAlbum));
         for (Map.Entry<String, List<Audio>> entry: album.entrySet()) {
             {
                 al.add(new arrayliststore(entry.getKey()));
             }
         }
  }

            }