package com.jim.demo1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

        button = (Button) findViewById(R.id.testPOST);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Truster t = new Truster();
                t.run();
                postServer();
            }

        });

    }

    public void postServer() {

        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("username", "test6");
        jsonParams.put("password", "test");
        String url = "https://140.192.30.230:8443/beertrader/rest/user/createUser";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
         ) {
            @Override
            public Map<String, String> getHeaders()
            {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(request);

        /*String url = "https://140.192.30.230:8443/beertrader/rest/user/createUser";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username","test6");
                params.put("password","test");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(sr);*/
    }


    }



