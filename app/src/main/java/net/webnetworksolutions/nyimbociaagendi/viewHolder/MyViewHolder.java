package net.webnetworksolutions.nyimbociaagendi.viewHolder;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.webnetworksolutions.nyimbociaagendi.R;
import net.webnetworksolutions.nyimbociaagendi.other.ItemClickListener;
import net.webnetworksolutions.nyimbociaagendi.pojo.Song;

import java.util.ArrayList;

/**
 * Created by Benja on 8/14/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView img;
    public TextView title;
    public TextView lyrics;
    Context ctx;
    ArrayList<Song> songs=new ArrayList<>();

    ItemClickListener itemClickListener;

    public MyViewHolder(View itemView,Context ctx,ArrayList<Song>songs) {
        super(itemView);

        this.ctx=ctx;
        this.songs=songs;
        this.img=(ImageView)itemView.findViewById(R.id.img);
        this.title=(TextView)itemView.findViewById(R.id.songTitle);
        this.lyrics=(TextView)itemView.findViewById(R.id.songLyrics);
        lyrics.setVisibility(View.GONE);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic){
        this.itemClickListener=ic;
    }
}
