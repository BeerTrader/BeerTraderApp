package com.jim.demo1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButtonProfile();
        addListenerOnButtonSearch();
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


    public void addListenerOnButtonSearch() {

        final Context context = this;

        button = (Button) findViewById(R.id.searchButton);

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



