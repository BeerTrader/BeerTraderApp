package com.jim.demo1.Matches;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jim.demo1.MainActivity;
import com.jim.demo1.Objects.Match;
import com.jim.demo1.R;
import com.jim.demo1.Tools.Config;
import com.jim.demo1.Tools.PreferencesManager;
import com.jim.demo1.Tools.Truster;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Jim on 2/2/2015.
 */
public class matches_activity extends Activity {

    ArrayList<Match> matches = new ArrayList<>();
    String offerer;
    String desirer;
    String offerable;
    String desirable;
    Button acceptBtn;
    Button rejectBtn;
    TextView matchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches_layout);
        setAccpetBtn();
        setRejectBtn();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            matchText = (TextView) findViewById(R.id.offerer);
            matches = intent.getParcelableArrayListExtra("matches");
            Match match = matches.get(0);
            offerer = match.getOfferer().getUsername().toString();
            desirer = match.getDesirer().getUsername().toString();
            offerable = match.getOfferable().getName().toString();
            desirable = match.getDesirable().getName().toString();
            matchText.setText(offerer + " is offering " + offerable + " and " + desirer + " wants " + desirable);
        }
        else{
            showAlert2();
        }
    }

    private void setAccpetBtn() {
        final String acceptUrl = "https://140.192.30.230:8443/beertrader/rest/match/acceptMatch";
        acceptBtn = (Button) findViewById(R.id.acceptBtn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                matchText.setText("");
                try{
                    JSONObject response = new ACCEPT().execute(acceptUrl, matches.get(0).toString()).get();
                    //save the channel
                    //TODO the channel size is not updating correctly
                    ArrayList<String> channels = PreferencesManager.getInstance(getApplicationContext()).loadChannels();
                    channels.add(response.get("channelName").toString());
                    PreferencesManager.getInstance(getApplicationContext()).saveChannels(channels);
                    ArrayList<String> channelstest = PreferencesManager.getInstance(getApplicationContext()).loadChannels();
                    //save the token
                    Config.TOKEN = response.get("authenticationToken").toString();
                }catch (InterruptedException e) { e.printStackTrace();}
                catch (ExecutionException e) { e.printStackTrace();}
                catch (JSONException e) { e.printStackTrace();}
                matches.remove(0);
                if (!matches.isEmpty()) {
                    Match match = matches.get(0);
                    offerer = match.getOfferer().getUsername().toString();
                    desirer = match.getDesirer().getUsername().toString();
                    offerable = match.getOfferable().getName().toString();
                    desirable = match.getDesirable().getName().toString();
                    matchText.setText(offerer + " is offering " + offerable + " and " + desirer + " wants " + desirable);
                }
                else{
                    showAlert2();

                }
            }

        });
    }

    private void setRejectBtn() {
        final String rejectURL = "https://140.192.30.230:8443/beertrader/rest/match/rejectMatch";
        rejectBtn = (Button) findViewById(R.id.rejectBtn);
        rejectBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                matchText.setText("");
                new REJECT().execute(rejectURL, matches.get(0).toString());
                matches.remove(0);
                if (!matches.isEmpty()) {
                    Match match = matches.get(0);
                    offerer = match.getOfferer().getUsername().toString();
                    desirer = match.getDesirer().getUsername().toString();
                    offerable = match.getOfferable().getName().toString();
                    desirable = match.getDesirable().getName().toString();
                    matchText.setText(offerer + " is offering " + offerable + " and " + desirer + " wants " + desirable);
                } else {
                    showAlert2();
                }

            }

        });
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

            // display box
            alertbox.show();
    }

    class ACCEPT extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
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
                System.out.println("Status Code = " + httpResponse.getStatusLine().getStatusCode());
                HttpEntity entity = httpResponse.getEntity();
                String response = EntityUtils.toString(entity);
                System.out.println(response.toString());
                //TODO wont work for reject, its not a json object
                JSONObject responseObject = new JSONObject(response);
                return responseObject;
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

    class REJECT extends AsyncTask<String, Void, Void> {
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
}