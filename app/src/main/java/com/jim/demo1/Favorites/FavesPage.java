package com.jim.demo1.Favorites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jim.demo1.Post.Beer;
import com.jim.demo1.Post.CustomAdapter;
import com.jim.demo1.R;
import com.jim.demo1.Tools.PreferencesManager;
import com.jim.demo1.Tools.RequestSingleton;
import com.jim.demo1.Tools.Truster;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Jim on 5/11/2015.
 */
public class FavesPage extends Activity{

    private String url3 = "&client_id=28CF9AABA9878838EAAA37110B0B38FD6B8A3CC0&client_secret=B6DB39BF04ECFD467E7B33D129AD9E99D4DA2ABE&limit=25";
    private ArrayList<Beer> beers = new ArrayList<>();
    private ArrayList styles = new ArrayList<>();
    private CustomAdapter adapter;
    private ArrayAdapter styleAdapter;
    public ListView listView;

    String[] list_items = {"Abbey Dubbel", "Abbey Quadrupel", "Abbey Tripel", "Ale", "Altbier", "Amber Ale",
            "American Barleywine", "American Black Ale", "American Pale Ale", "American Wheat", "Baltic Porter",
            "Belgian Ale", "Belgian Strong Ale", "Berliner Weisse", "Biere de Garde", "Bitter", "Blonde Ale",
            "Bock", "Bohemian / Czech Pilsener", "Brown Ale", "California Common / Steam", "Chocolate Stout",
            "Cider", "Coffee Stout", "Cream Ale", "Doppelbock", "Dry Stout", "Dunkel / Dark Lager",
            "Dunkel Weizen", "Eisbock", "English Barleywine", "English Pale Ale", "English Strong Ale",
            "Euro Lager", "Extra Special Bitter", "Faro", "Flanders Oude Bruin", "Flanders Red", "Foreign / Extra Stout",
            "Fruit Beer", "Fruit Lambic", "Fruit Stout", "Gueuze", "Hefeweizen", "Helles / Dortmunder",
            "Imperial IPA", "Imperial Pilsener", "Imperial Porter", "Imperial Stout", "IPA", "Irish Ale",
            "Kellerbier", "Kölsch", "Krystal Weizen", "Lager", "Light / Lite Lager", "Marzen / Oktoberfest",
            "Mead", "Mild Ale", "Milk Stout", "Oatmeal Stout", "Old Ale", "Oyster Stout", "Pale Lager", "Porter",
            "Rauchbier", "Reduced Alcohol", "Rye Beer", "Saison", "Schwarzbier", "Scottish Ale", "Smoked Ale",
            "Spiced Beer", "Stout", "Strong Ale", "Strong Lager", "Unblended Lambic", "Vienna / Amber Lager",
            "Weizenbock", "Wheatwine", "Wild Ale", "Witbier",

    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favs_page_layout);
        listView = (ListView) findViewById(R.id.addFavsListView);
        adapter = new CustomAdapter(this, beers);
        styleAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, styles);
        final EditText inputSearch;
        final Button favsStylesButton;
        final Button favsBreweryButton;
        final Button favsBeerButton;


        inputSearch = (EditText) findViewById(R.id.editText);
        favsStylesButton = (Button) findViewById(R.id.FavStyle);
        favsBreweryButton = (Button) findViewById(R.id.FavBrewery);
        favsBeerButton = (Button) findViewById(R.id.FavBeer);

        listView.setAdapter(styleAdapter);
        styleAdapter.notifyDataSetChanged();
        //Search Button Listener



       favsBeerButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                beers.clear();
                getBeers(inputSearch.getText().toString());
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Beer beerSelect = new Beer (beers.get(position).getBeer_name(),
                                beers.get(position).getBrewery(), beers.get(position).getBeer_style(),
                                beers.get(position).getImgUrl());
                        //favs.add(beerSelect);
                        //PersistentData.favsInventory.add(beerSelect);
                        showBeerAlert(beerSelect);
                        Toast.makeText(getApplicationContext(),
                                (CharSequence) beers.get(position).getBeer_name(), Toast.LENGTH_SHORT).show();                    }
                });
            }

        });

        favsBreweryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beers.clear();
                getBrewery(inputSearch.getText().toString());
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Favs breweryFavSelect = new Favs((beers.get(position).getBeer_name()), "BREWERY");
                        showBrewStyleAlert(breweryFavSelect);

                        Toast.makeText(getApplicationContext(),
                                (CharSequence) beers.get(position).getBeer_name(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        favsStylesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beers.clear();
                getStyles(inputSearch.getText().toString());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = styleAdapter.getItem(position).toString();
                Favs styleFavSelect = new Favs(value, "BEERTYPE");
                showBrewStyleAlert(styleFavSelect);

                Toast.makeText(getApplicationContext(),
                        value, Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getStyles(String s) {
        styles.clear();
        for(int i = 0; i < list_items.length; i++){
            if(list_items[i].contains(s)){
                styles.add(list_items[i]);
            }
        }
        styleAdapter.notifyDataSetChanged();

    }


    public void getBeers(String searchText) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, ("https://api.untappd.com/v4/search/beer/?q="  + searchText + url3), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();

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

    public void getBrewery(String searchText) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, ("https://api.untappd.com/v4/search/brewery/?q=" + searchText + url3), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();

                            //Tree Model

                            JsonNode root = objectMapper.readTree(response.toString());

                            int count = root.get("response").get("brewery").get("count").asInt();

                            for (int i = 0; i < count; i++) {
                                String breweryName = root.get("response").get("brewery").get("items").get(i).get("brewery").get("brewery_name").toString();
                                String beerBrewery = root.get("response").get("brewery").get("items").get(i).get("brewery").get("country_name").toString();
                                String beerStyle = root.get("response").get("brewery").get("items").get(i).get("brewery").get("location").get("brewery_city").toString();
                                String BeerLabel = root.get("response").get("brewery").get("items").get(i).get("brewery").get("brewery_label").toString();
                                String urlEnd = BeerLabel.split("https://d1c8v1qci5en44", 2)[1];
                                urlEnd = urlEnd.substring(0, urlEnd.length() - 1);
                                try {
                                    BeerLabel = "https://" + URLEncoder.encode("d1c8v1qci5en44", "UTF-8") + urlEnd;
                                }catch (UnsupportedEncodingException e ) {e.printStackTrace();}
                                beers.add(new Beer(breweryName, beerBrewery, beerStyle, BeerLabel));
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

    private void showBeerAlert(final Beer beerSelect) {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        alertbox.setMessage("Do you want to Add This Beer to your Favorites?");

        alertbox.setPositiveButton("Add", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub Add Beer to Inventory
                String postUrl = "https://140.192.30.230:8443/beertrader/rest/desirable/addDesirable";
                new POSTbeerFav().execute(postUrl, beerSelect.getBeer_name(), beerSelect.getBrewery(), beerSelect.getBeer_style());
                Favs b = new Favs(beerSelect.getBeer_name(), "BEER");
                PreferencesManager.getInstance(getApplicationContext()).addFavorites(b);

                Toast.makeText(getApplicationContext(), "Beer Added", Toast.LENGTH_SHORT).show();

            }
        });

        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        // display box
        alertbox.show();
    }


    private void showBrewStyleAlert(final Favs beerSelect) {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        alertbox.setMessage("Do you want to Add This to your Favorites?");

        alertbox.setPositiveButton("Add", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub Add Beer to Inventory
                //{"type": "BEERTYPE", "name": "PaleAle"}
                String postUrl = "https://140.192.30.230:8443/beertrader/rest/desirable/addDesirable";
                new POSTBrewStyleFav().execute(postUrl, beerSelect.getFav_name(), beerSelect.getFav_type());
                Favs b = new Favs(beerSelect.getFav_name(), "BREWERY");
                PreferencesManager.getInstance(getApplicationContext()).addFavorites(b);

                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();

            }
        });

        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        });

        // display box
        alertbox.show();
    }


    class POSTBrewStyleFav extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            String beerName = params[1];
            String beerType = params[2];
            JSONObject jsonobj = new JSONObject();
            try {
                jsonobj.put("type", beerType);
                jsonobj.put("name", beerName);
            } catch(JSONException e) {
                e.printStackTrace();
            }

            Truster t = new Truster();
            HttpClient httpClient = t.getNewHttpClient();

            HttpPost httpPostReq = new HttpPost(url);
            httpPostReq.setHeader("Authorization", PreferencesManager.getInstance(getApplicationContext()).loadAuthorization());
            try{
                StringEntity se = new StringEntity(jsonobj.toString(), "UTF-8");
                se.setContentType("application/json; charset=UTF-8");
                httpPostReq.setEntity(se);
            } catch (UnsupportedEncodingException e) {e.printStackTrace();}
            try{
                HttpResponse httpResponse = httpClient.execute(httpPostReq);
                //TODO REMOVE!!!
                System.out.println("Status Code = " + httpResponse.getStatusLine().getStatusCode());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class POSTbeerFav extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            String beerName = params[1];
            String beerType = params[2];
            String brewery = params[3];
            JSONObject jsonobj = new JSONObject();
            JSONObject jsonobj1 = new JSONObject();
            JSONObject jsonobj2 = new JSONObject();

            JSONArray jsonarry = new JSONArray();
            try {
                jsonobj.put("label", "BEER");
                jsonobj.put("name", beerName);
                jsonobj1.put("label", "BEERTYPE");
                jsonobj1.put("name", beerType);
                jsonobj2.put("label", "BREWERY");
                jsonobj2.put("name", brewery);
                jsonarry.put(jsonobj1);
                jsonarry.put(jsonobj2);
                jsonobj.put("relations", jsonarry);
            } catch(JSONException e) {
                e.printStackTrace();
            }

            Truster t = new Truster();
            HttpClient httpClient = t.getNewHttpClient();

            HttpPost httpPostReq = new HttpPost(url);
            httpPostReq.setHeader("Authorization", PreferencesManager.getInstance(getApplicationContext()).loadAuthorization());
            try{
                StringEntity se = new StringEntity(jsonobj.toString(), "UTF-8");
                se.setContentType("application/json; charset=UTF-8");
                httpPostReq.setEntity(se);
            } catch (UnsupportedEncodingException e) {e.printStackTrace();}
            try{
                HttpResponse httpResponse = httpClient.execute(httpPostReq);
                //TODO REMOVE!!!
                System.out.println("Status Code = " + httpResponse.getStatusLine().getStatusCode());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}





