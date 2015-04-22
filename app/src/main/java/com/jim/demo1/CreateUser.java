package com.jim.demo1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


public class CreateUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        final EditText textName = (EditText)findViewById(R.id.loginName);
        final EditText textPwd = (EditText)findViewById(R.id.loginPwd);
        final Button signUpBtn = (Button)findViewById(R.id.signUp);
        final Button loginBtn = (Button)findViewById(R.id.login);
        final String signUpURL = "https://140.192.30.230:8443/beertrader/rest/user/createUser";
        final String loginURL = "https://140.192.30.230:8443/beertrader/rest/user/login";

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
}
