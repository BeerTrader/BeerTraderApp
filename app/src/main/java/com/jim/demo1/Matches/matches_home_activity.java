package com.jim.demo1.Matches;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jim.demo1.Exceptions.ObjectMappingException;
import com.jim.demo1.Objects.Match;
import com.jim.demo1.Objects.ObjectManager;
import com.jim.demo1.R;
import com.jim.demo1.Tools.PersistentData;
import com.jim.demo1.Tools.Truster;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jim on 5/25/2015.
 */
public class matches_home_activity extends Activity {
    Button getMatchesButton;
    Button retrieveMatchesButton;
    final String matchesURL = "https://140.192.30.230:8443/beertrader/rest/match/getMatches";
    ArrayList<Match> matchList = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_home);
        getMatchesButton();
        retrieveMatchesButton();
    }

    private void retrieveMatchesButton() {
        retrieveMatchesButton = (Button) findViewById(R.id.retrieveMatchesButton);
        final Context context = this;
        retrieveMatchesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new getMatches().execute(matchesURL, PersistentData.authorization);
            }

        });
    }

    private void getMatchesButton() {
        getMatchesButton = (Button) findViewById(R.id.getMatchesButton);
        final Context context = this;
        getMatchesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, matches_activity.class);
                intent.putParcelableArrayListExtra("matches", matchList);
                startActivity(intent);
            }

        });
    }

    class getMatches extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            String token = params[1];

            Truster t = new Truster();
            HttpClient httpClient = t.getNewHttpClient();

            HttpGet httpGetReq = new HttpGet(url);
            httpGetReq.addHeader("Authorization", token);
            try{
                HttpResponse httpResponse = httpClient.execute(httpGetReq);
                //TODO REMOVE!!!
                System.out.println("Status Code = " + httpResponse.getStatusLine().getStatusCode());
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity);

                //build out the arraylist of matches
                JSONObject obj = new JSONObject(response);
                final JSONArray matches = obj.getJSONArray("matchList");
                final int n = matches.length();
                for (int i = 0; i < n; ++i) {
                    final JSONObject object = matches.getJSONObject(i);
                    try{
                        Match match = (Match) ObjectManager.readObjectAsString(object.toString(), Match.class);
                        matchList.add(match);
                    } catch(ObjectMappingException e) { e.printStackTrace();}
                }
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
