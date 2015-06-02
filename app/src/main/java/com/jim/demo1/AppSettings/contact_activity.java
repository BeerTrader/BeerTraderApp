package com.jim.demo1.AppSettings;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jim.demo1.R;
import com.jim.demo1.Tools.PreferencesManager;
import com.jim.demo1.domains.Message;
import com.jim.demo1.handler.OrtcHandler;
import com.jim.demo1.interfaces.InterfaceRefresher;
import com.jim.demo1.messaging.ChatActivity;
import com.jim.demo1.service.MyService;

import java.util.ArrayList;

/**
 * Created by Jim on 2/17/2015.
 */
public class contact_activity extends Activity implements InterfaceRefresher {
    ListView listView;
    String name;
    String channel;
    private Context contex = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_layout);
        listView = (ListView) findViewById(R.id.list);

        ArrayList<String> channels = PreferencesManager.getInstance(getApplicationContext()).loadChannels();
        if(channels == null) {
            channels = new ArrayList<>();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, channels);
        listView.setAdapter(adapter);

        OrtcHandler.prepareClient(getApplicationContext(), this);
        if(!isMyServiceRunning(MyService.class)) {
            startService(new Intent(this, MyService.class));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                channel = (String) listView.getItemAtPosition(itemPosition);
                name = PreferencesManager.getInstance(getApplicationContext()).loadUser();
                System.out.println("name = " + name);
                OrtcHandler.subscribeChannel(channel);
                Intent messages = new Intent(contex, ChatActivity.class).putExtra("channel", channel).putExtra("user", name);
                startActivity(messages);
            }
        });
    }

    @Override
    public void refreshData(Message msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ORTC is running");
            }
        });

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }



}
