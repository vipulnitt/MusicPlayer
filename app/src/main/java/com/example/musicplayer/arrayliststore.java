package com.example.musicplayer;


import java.util.ArrayList;

public class arrayliststore {
 String artist;
 ArrayList<Audio> arrayList;
 ArrayList<Audio> arrayListal;

    public ArrayList<Audio> getArrayListal() {
        return arrayListal;
    }

    public void setArrayListal(ArrayList<Audio> arrayListal) {
        this.arrayListal = arrayListal;
    }

    public ArrayList<Audio> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Audio> arrayList) {
        this.arrayList = arrayList;
    }
    arrayliststore(String artist)
{
    this.artist=artist;
    ArrayList<Audio> xarrayList = new ArrayList<Audio>();
    ArrayList<Audio> xyarrayList = new ArrayList<Audio>();
    for(int i=0;i<MainActivity.audioList.size();i++)
    {
        if(this.artist==MainActivity.audioList.get(i).getArtist())
        {
            xarrayList.add(MainActivity.audioList.get(i));
        }
        if(this.artist==MainActivity.audioList.get(i).getAlbum())
        {
            xyarrayList.add(MainActivity.audioList.get(i));
        }
    }
    this.arrayListal=xyarrayList;
    this.arrayList=xarrayList;
}
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
