package com.jim.demo1.Matches;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.jim.demo1.MainActivity;
import com.jim.demo1.Objects.Match;
import com.jim.demo1.R;
import com.jim.demo1.Tools.PreferencesManager;
import com.jim.demo1.Tools.Truster;
import com.jim.demo1.lorentzos.SwipeFlingAdapterView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class matches_activity extends Activity {

    ArrayList<Match> matches = new ArrayList<>();
 //   ArrayList<Match> matches2 = new ArrayList<>();
    ArrayList<String> matchString = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    String offerer;
    String desirer;
    String offerable;
    String desirable;



    @InjectView(R.id.frame)
    SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches_layout);
        ButterKnife.inject(this);
//        matches2.add(new Match(
//                1433610846,
//                (new User((long) 1433610846, "jim2", "yes", 41.909909909909906, -87.66943241842068)),
//                (new User((long) 1433550221, "jim3", "yes", 41.909909909909906, -87.66943241842068)),
//                (new TradingEntity(1433610630, "BEER", "Blue Moon", null)),
//                (new TradingEntity(1433610631, "NAME", "Belgium White", null))));
//        matches2.add(new Match(
//                1433610847,
//                (new User((long) 1433610846, "jim2", "yes", 41.909909909909906, -87.66943241842068)),
//                (new User((long) 1433550221, "jim3", "yes", 41.909909909909906, -87.66943241842068)),
//                (new TradingEntity(1433610630, "BEER", "Three Floyds", null)),
//                (new TradingEntity(1433610631, "NAME", "Alpha King", null))));
//        matches2.add(new Match(
//                1433610848,
//                (new User((long) 1433610846, "jim2", "yes", 41.909909909909906, -87.66943241842068)),
//                (new User((long) 1433550221, "jim3", "yes", 41.909909909909906, -87.66943241842068)),
//                (new TradingEntity(1433610630, "BEER", "Coors", null)),
//                (new TradingEntity(1433610631, "NAME", "Coors Light", null))));
//        matches2.add(new Match(
//                1433610849,
//                (new User((long) 1433610846, "jim2", "yes", 41.909909909909906, -87.66943241842068)),
//                (new User((long) 1433550221, "jim3", "yes", 41.909909909909906, -87.66943241842068)),
//                (new TradingEntity(1433610630, "BEER", "Lagunitas", null)),
//                (new TradingEntity(1433610631, "NAME", "IPA", null))));
        Intent intent = getIntent();
        matches = intent.getParcelableArrayListExtra("matches");
        matchFix(matches);



        arrayAdapter = new ArrayAdapter<>(this, R.layout.matches_item, R.id.MatchOffer, matchString );
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            int theMatch = matches.size();
            @Override
            public void removeFirstObjectInAdapter() {
                Log.d("LIST", "removed object!");
//                matches.remove(0);
//                matchString.remove(0);
                matches.get(0);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                final String rejectURL = "https://140.192.30.230:8443/beertrader/rest/match/rejectMatch";
                new ACCEPT_REJECT().execute(rejectURL, matches.get(0).toString());

                arrayAdapter.notifyDataSetChanged();
            }



            @Override
            public void onRightCardExit(Object dataObject)
            {
                final String acceptUrl = "https://140.192.30.230:8443/beertrader/rest/match/acceptMatch";

                new ACCEPT_REJECT().execute(acceptUrl, matches.get(0).toString());
                matches.remove(0);
                matchString.remove(0);
                arrayAdapter.notifyDataSetChanged();

            }



            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                if(itemsInAdapter == 0){
                    showAlert2();
                }
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.match_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.match_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

    }


    @OnClick(R.id.right)
    public void right() {

        flingContainer.getTopCardListener().selectRight();
        final String acceptUrl = "https://140.192.30.230:8443/beertrader/rest/match/acceptMatch";

        new ACCEPT_REJECT().execute(acceptUrl, matches.get(0).toString());
        arrayAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.left)
    public void left() {

        flingContainer.getTopCardListener().selectLeft();
        final String rejectURL = "https://140.192.30.230:8443/beertrader/rest/match/rejectMatch";
        new ACCEPT_REJECT().execute(rejectURL, matches.get(0).toString());

        arrayAdapter.notifyDataSetChanged();
    }

    private void showAlert2() {
        final Context context = this;
        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        alertbox.setMessage("NO MORE MATCHES");

        alertbox.setPositiveButton("More Matches", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(context, matches_home_activity.class);
                startActivity(intent);

            }
        });

        alertbox.setNegativeButton("Home", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        alertbox.show();
    }

    class ACCEPT_REJECT extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            String match = params[1];
            JSONObject jsonobj = null;
            try {
                jsonobj = new JSONObject(match);
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
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity);
                System.out.println(response.toString());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void matchFix(ArrayList<Match> al) {
        for(int i = 0; i < al.size(); i++){
            al.get(i);

            String eol = System.getProperty("line.separator");
            offerer = al.get(i).getOfferer().getUsername().toString();
            desirer = al.get(i).getDesirer().getUsername().toString();
            offerable = al.get(i).getOfferable().getName().toString();
            desirable = al.get(i).getDesirable().getName().toString();
            matchString.add(offerer + " is offering " + eol +
                    offerable + eol +
                    " and " + desirer + eol +
                    " wants " + eol +
                    desirable);
//            System.out.println(al.get(i));
//            System.out.println(al.get(i).getDesirable().toString());
        }
    }
}