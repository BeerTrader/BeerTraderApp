package com.jim.demo1.Inventory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.jim.demo1.R;
import com.jim.demo1.Tools.PreferencesManager;

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
        adapter = new Inventory_Adapter(this, PreferencesManager.getInstance(getApplicationContext()).getInventory());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



}
