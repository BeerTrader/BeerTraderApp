package com.jim.demo1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Jim on 2/2/2015.
 */
public class post_activity extends Activity
        //implements SearchView.OnQueryTextListener, SearchView.OnCloseListener
{

//    private ImageButton button;
    private String url = "https://api.untappd.com/v4/search/beer/?q=Stella&client_id=28CF9AABA9878838EAAA37110B0B38FD6B8A3CC0&client_secret=B6DB39BF04ECFD467E7B33D129AD9E99D4DA2ABE&limit=25";
    private ArrayList<Beer> beers = new ArrayList<>();
    private CustomAdapter adapter;
    //private SearchView searchView;
    private ListView tv;
    //private TextView resultText;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_layout);
        tv= (ListView) findViewById(R.id.list);
        adapter = new CustomAdapter(this, beers);
        //resultText = (TextView)findViewById(R.id.searchViewResult);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();

                            //Tree Model

                            JsonNode root = objectMapper.readTree(response.toString());

                            int count = root.get("response").get("beers").get("count").asInt();


                            for (int i = 0; i < count; i++) {
                                beers.add(new Beer((root.get("response").get("beers").get("items").get(i).get("beer").get("beer_name").toString()),
                                        (root.get("response").get("beers").get("items").get(i).get("brewery").get("brewery_name").toString()),
                                        (root.get("response").get("beers").get("items").get(i).get("beer").get("beer_style").toString()),
                                        (root.get("response").get("beers").get("items").get(i).get("beer").get("beer_label").toString())));

                                System.out.println(beers.get(i).getBeer_name().toString());
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

//List Item Click Listener

        RequestSingleton.getInstance(this).addToRequestQueue(jsObjRequest);
        //Volley.newRequestQueue(this).add(jsObjRequest);



    }


//    private class GetContacts extends AsyncTask<Void, Void, Void> {
//
//        private Context mContext;
//
//        public GetContacts(Context context) {
//            mContext = context;
//
//        }
//
//        RequestSingleton.getInstance(mContext).
//
//        addToRequestQueue(req);
//
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            return null;
//        }
//    }




//
//    @Override
//    public boolean onClose() {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        return false;
//    }
}

