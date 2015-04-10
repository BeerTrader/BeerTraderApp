package com.jim.demo1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButtonProfile();
        addListenerOnButtonMatches();
        addListenerOnButtonPost();
        addListenerOnButtonPostTest();
    }



    public void addListenerOnButtonProfile() {

        final Context context = this;

        button = (Button) findViewById(R.id.profileButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, profile_activity.class);
                startActivity(intent);

            }

        });

    }


   public void addListenerOnButtonPost() {

        final Context context = this;

        button = (Button) findViewById(R.id.postButton);

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

        button = (Button) findViewById(R.id.matchesButton);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, matches_activity.class);
                startActivity(intent);

            }

        });

    }

    public void addListenerOnButtonPostTest() {

        final Context context = this;

        button = (Button) findViewById(R.id.createUser);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, CreateUser.class);
                startActivity(intent);

            }

        });

    }

    }



