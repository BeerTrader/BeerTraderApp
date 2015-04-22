package com.jim.demo1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by Jim on 4/13/2015.
 */
public class Inventory extends Activity {

    private Inventory_Adapter adapter;
    public ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_layout);
        listView = (ListView) findViewById(R.id.list);
        adapter = new Inventory_Adapter(this, PersistentData.beerInventory);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



}
