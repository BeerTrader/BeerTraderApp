package com.jim.demo1;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class CreateUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        final EditText textName = (EditText)findViewById(R.id.loginName);
        final EditText textPwd = (EditText)findViewById(R.id.loginPwd);
        final Button btn = (Button)findViewById(R.id.submitUser);
        final String url = "https://140.192.30.230:8443/beertrader/rest/user/createUser";
        Truster t = new Truster();
        t.run();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String uName = textName.getText().toString();
                String uPwd = textPwd.getText().toString();
                new POST().execute(url, uName, uPwd);
            }

        });

    }

    class POST extends AsyncTask<String, Void, Void> {
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

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPostReq = new HttpPost(url);
            try{
                System.out.println(jsonobj.toString());
                StringEntity se = new StringEntity(jsonobj.toString(), "UTF-8");
                se.setContentType("application/json; charset=UTF-8");
                //se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
                httpPostReq.setEntity(se);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try{
                HttpResponse httpResponse = httpClient.execute(httpPostReq);
                System.out.println("response got here");
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
