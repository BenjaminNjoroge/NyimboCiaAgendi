package net.webnetworksolutions.nyimbociaagendi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import net.webnetworksolutions.nyimbociaagendi.R;
import net.webnetworksolutions.nyimbociaagendi.activity.DisplayActivity;
import net.webnetworksolutions.nyimbociaagendi.other.CustomFilter;
import net.webnetworksolutions.nyimbociaagendi.other.ItemClickListener;
import net.webnetworksolutions.nyimbociaagendi.pojo.Song;
import net.webnetworksolutions.nyimbociaagendi.viewHolder.MyViewHolder;

import java.util.ArrayList;

/**
 * Created by Benja on 8/14/2017.
 */

public class SongBookAdapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable{

   Context c;
    public ArrayList<Song>songs;
    ArrayList<Song> filterList;
    CustomFilter filter;

    public SongBookAdapter(Context ctx, ArrayList<Song> songs) {
        this.c = ctx;
        this.songs = songs;
        this.filterList=songs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);
        MyViewHolder holder=new MyViewHolder(v,c,songs);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.img.setImageResource(songs.get(position).getPhoto());
        holder.title.setText(songs.get(position).getTitle());
        holder.lyrics.setText(songs.get(position).getLyrics());

        //implement click listener
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

               Song song=songs.get(pos);
                Intent intent=new Intent(c, DisplayActivity.class);
                intent.putExtra("img_id",song.getPhoto());
                intent.putExtra("name",song.getTitle());
                intent.putExtra("lyrics",song.getLyrics());
                c.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }


    @Override
    public Filter getFilter() {
        if (filter==null){
            filter=new CustomFilter(filterList,this);
        }
        return filter;
    }
}
