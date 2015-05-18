package com.jim.demo1.Favorites;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jim.demo1.R;

import java.util.List;

/**
 * Created by Jim on 5/12/2015.
 */
public class Favs_Adapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Favs> favoriteList;



    public Favs_Adapter(Activity activity, List<Favs> fList) {
        this.activity = activity;
        this.favoriteList = fList;
    }

    @Override
    public int getCount() {
        return favoriteList.size();
    }

    @Override
    public Object getItem(int position) {
        return favoriteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (convertView == null) {
            if (inflater == null)
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.favs_items, parent, false);
        }

        TextView favItemName = (TextView) row.findViewById(R.id.FavItemName);
        TextView favItemType = (TextView) row.findViewById(R.id.favItemType);

        Favs b = favoriteList.get(position);
        favItemName.setText(b.getFav_name());
        favItemType.setText(b.getFav_type());
        return row;
    }

}
