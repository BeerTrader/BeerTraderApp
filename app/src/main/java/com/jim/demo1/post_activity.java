package com.jim.demo1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Created by Jim on 2/2/2015.
 */
public class post_activity extends Activity {

    private String url1 = "https://api.untappd.com/v4/search/beer/?q=";
    private String url2 = "&client_id=28CF9AABA9878838EAAA37110B0B38FD6B8A3CC0&client_secret=B6DB39BF04ECFD467E7B33D129AD9E99D4DA2ABE&limit=25";
    private ArrayList<Beer> beers = new ArrayList<>();
    private CustomAdapter adapter;
    public ListView listView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomAdapter(this, beers);
        final EditText inputSearch;
        final Button searchBeersButton;

        inputSearch = (EditText) findViewById(R.id.beerSearchText);
        searchBeersButton = (Button) findViewById(R.id.searchBeersButton);
        listView.setAdapter(adapter);

        //List Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(post_activity.this, add_beer_activity.class);
                intent.putExtra("imgUrl", beers.get(position).getImgUrl());
                intent.putExtra("beerName", beers.get(position).getBeer_name());
                intent.putExtra("brewery", beers.get(position).getBrewery());
                intent.putExtra("beerType", beers.get(position).getBeer_style());
                startActivity(intent);
            }
        });

        //Search Button Listener
        searchBeersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beers.clear();
                getBeers(inputSearch.getText().toString());
            }
        });

    }

    public void getBeers(String searchText) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, (url1 + searchText + url2), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();

                            //Tree Model

                            JsonNode root = objectMapper.readTree(response.toString());

                            int count = root.get("response").get("beers").get("count").asInt();

                            for (int i = 0; i < count; i++) {
                                String beerName = root.get("response").get("beers").get("items").get(i).get("beer").get("beer_name").toString();
                                String beerBrewery = root.get("response").get("beers").get("items").get(i).get("brewery").get("brewery_name").toString();
                                String beerStyle = root.get("response").get("beers").get("items").get(i).get("beer").get("beer_style").toString();
                                String BeerLabel = root.get("response").get("beers").get("items").get(i).get("beer").get("beer_label").toString();
                                String urlEnd = BeerLabel.split("https://d1c8v1qci5en44", 2)[1];
                                urlEnd = urlEnd.substring(0, urlEnd.length() - 1);
                                try {
                                    BeerLabel = "https://" + URLEncoder.encode("d1c8v1qci5en44", "UTF-8") + urlEnd;
                                }catch (UnsupportedEncodingException e ) {e.printStackTrace();}
                                beers.add(new Beer(beerName, beerBrewery, beerStyle, BeerLabel));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        RequestSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest);
    }
}

