package com.jim.demo1.Favorites;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.jim.demo1.R;
import com.jim.demo1.Tools.PersistentData;

/**
 * Created by Jim on 5/11/2015.
 */
public class Favorites extends Activity{

    private Favs_Adapter adapter;
    public ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favs_list);
        listView = (ListView) findViewById(R.id.myFavoritesList);
        adapter = new Favs_Adapter(this, PersistentData.favsInventory);
        listView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }
}
