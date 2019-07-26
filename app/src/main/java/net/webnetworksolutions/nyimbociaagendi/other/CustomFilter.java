package net.webnetworksolutions.nyimbociaagendi.other;

import android.widget.Filter;

import net.webnetworksolutions.nyimbociaagendi.adapter.SongBookAdapter;
import net.webnetworksolutions.nyimbociaagendi.pojo.Song;

import java.util.ArrayList;

/**
 * Created by Benja on 8/10/2017.
 */

public class CustomFilter extends Filter{

    private SongBookAdapter adapter;
    private ArrayList<Song>filterList;

    public CustomFilter(ArrayList<Song> filterList,SongBookAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    //filter occurs

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //check constraint validity
        if (constraint!=null&&constraint.length()>0){
            //change to string
            constraint=constraint.toString().toUpperCase();
            //store filtered Songs
            ArrayList<Song>filteredSongs=new ArrayList<>();

            for (int i=0;i<filterList.size();i++){
                //check
                if (filterList.get(i).getTitle().toUpperCase().contains(constraint)){
                    filteredSongs.add(filterList.get(i));
                }
            }
            results.count=filteredSongs.size();
            results.values=filteredSongs;
        } else {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.songs=(ArrayList<Song>)results.values;
        //refresh
        adapter.notifyDataSetChanged();

    }
}
