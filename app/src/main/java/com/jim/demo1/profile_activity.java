package com.jim.demo1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Jim on 2/2/2015.
 */
public class profile_activity extends Activity {



    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        addListenerOnButtonAppSettings();
        addListenerOnButtonContact();
    }

    public void addListenerOnButtonAppSettings() {

        final Context context = this;

        button = (Button) findViewById(R.id.profileAppSettingsButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, app_settings_activity.class);
                startActivity(intent);

            }

        });

    }

    public void addListenerOnButtonContact() {

        final Context context = this;

        button = (Button) findViewById(R.id.profileContactButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, contact_activity.class);
                startActivity(intent);

            }

        });

    }
}
