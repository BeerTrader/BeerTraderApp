package com.jim.demo1.AppSettings;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.jim.demo1.R;
import com.jim.demo1.Tools.PersistentData;
import com.jim.demo1.Tools.Truster;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class CreateUser extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    EditText textName;
    EditText textPwd;
    Button signUpBtn;
    Button loginBtn;
    String signUpURL;
    String loginURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        textName = (EditText)findViewById(R.id.loginName);
        textPwd = (EditText)findViewById(R.id.loginPwd);
        signUpBtn = (Button)findViewById(R.id.signUp);
        loginBtn = (Button)findViewById(R.id.login);
        signUpURL = "https://140.192.30.230:8443/beertrader/rest/user/createUser";
        loginURL = "https://140.192.30.230:8443/beertrader/rest/user/login";
        buildGoogleApiClient();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String uName = textName.getText().toString();
                String uPwd = textPwd.getText().toString();
                new SignUp().execute(signUpURL, uName, uPwd);
            }

        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = textName.getText().toString();
                String uPwd = textPwd.getText().toString();
                new Login().execute(loginURL, uName, uPwd);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            PersistentData.latitude = String.valueOf(mLastLocation.getLatitude());
            PersistentData.longitude = String.valueOf(mLastLocation.getLongitude());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    class SignUp extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            String name = params[1];
            String password = params[2];
            JSONObject jsonobj = new JSONObject();
            try {
                jsonobj.put("username", name);
                jsonobj.put("password", password);
                //TODO update these with the real long and lat
                jsonobj.put("latitude", PersistentData.latitude);
                jsonobj.put("longitude", PersistentData.longitude);
            } catch(JSONException e) {
                e.printStackTrace();
            }

            Truster t = new Truster();
            HttpClient httpClient = t.getNewHttpClient();

            HttpPost httpPostReq = new HttpPost(url);
            try{
                StringEntity se = new StringEntity(jsonobj.toString(), "UTF-8");
                se.setContentType("application/json; charset=UTF-8");
                httpPostReq.setEntity(se);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try{
                HttpResponse httpResponse = httpClient.execute(httpPostReq);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class Login extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            String name = params[1];
            String password = params[2];

            Truster t = new Truster();
            HttpClient httpClient = t.getNewHttpClient();

            HttpGet httpGetReq = new HttpGet(url);
            String creds = name + ":" + password;
            String encode = Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
            httpGetReq.addHeader("Authorization", "Basic " + encode);
            try{
                HttpResponse httpResponse = httpClient.execute(httpGetReq);
                //TODO REMOVE!!!
                System.out.println("Status Code = " + httpResponse.getStatusLine().getStatusCode());
                HttpEntity entity = httpResponse.getEntity();
                PersistentData.authorization = EntityUtils.toString(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
