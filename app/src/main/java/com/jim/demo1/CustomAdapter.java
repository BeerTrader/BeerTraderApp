package com.jim.demo1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

/**
 * Created by Jim on 4/7/2015.
 */
public class CustomAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Beer> beerList;
    ImageLoader mImageLoader;
    ImageView pic;

    public CustomAdapter(Activity activity, List<Beer> bList) {
        this.activity = activity;
        this.beerList = bList;
    }
    @Override
    public int getCount() {
        return beerList.size();
    }

    @Override
    public Object getItem(int position) {
        return beerList.get(position);
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
            row = inflater.inflate(R.layout.beer_list_row, parent, false);
        }

        pic = (ImageView) row.findViewById(R.id.lablePic);
        TextView theBeerName = (TextView) row.findViewById(R.id.beerName);
        TextView theBreweryName = (TextView) row.findViewById(R.id.brewery);

        Beer b = beerList.get(position);
        theBeerName.setText(b.getBeer_name());
        theBreweryName.setText(b.getBrewery());
//        mImageLoader = RequestSingleton.getInstance().getImageLoader();
//        mImageLoader.get(b.getImgUrl(), ImageLoader.getImageListener(pic, 0, 0));
        return row;
    }
}
