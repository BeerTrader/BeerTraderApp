package com.jim.demo1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends Activity {

    ImageButton button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButtonProfile();
        addListenerOnButtonSearch();
        addListenerOnButtonMatches();
        addListenerOnButtonPost();
    }



    public void addListenerOnButtonProfile() {

        final Context context = this;

        button = (ImageButton) findViewById(R.id.profileButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, profile_activity.class);
                startActivity(intent);

            }

        });

    }


    public void addListenerOnButtonSearch() {

        final Context context = this;

        button = (ImageButton) findViewById(R.id.searchButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, search_activity.class);
                startActivity(intent);

            }

        });


    }

    public void addListenerOnButtonPost() {

        final Context context = this;

        button = (ImageButton) findViewById(R.id.postButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, post_activity.class);
                startActivity(intent);

            }

        });

    }

    public void addListenerOnButtonMatches() {

        final Context context = this;

        button = (ImageButton) findViewById(R.id.matchesButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, matches_activity.class);
                startActivity(intent);

            }

        });

    }


    }



