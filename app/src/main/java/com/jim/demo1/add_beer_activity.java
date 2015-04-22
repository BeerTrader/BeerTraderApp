package com.jim.demo1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Jim on 4/7/2015.
 */
public class add_beer_activity extends Activity{

    ImageButton addBeerButton;
    private String InvImgUrl;
    private TextView beerName;
    private TextView brewery;
    private TextView beerType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_beer_layout);
        beerName = (TextView) findViewById(R.id.beerName);
        brewery= (TextView) findViewById(R.id.brewery);
        beerType = (TextView) findViewById(R.id.beerType);
        //InvImgUrl = (ImageView) findViewById(R.id.lablePic);
        addListenerOnButton();
    }

    private void addListenerOnButton() {

        addBeerButton = (ImageButton) findViewById(R.id.imageButton);

        addBeerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showAlert();

            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            String imgUrl = intent.getCharSequenceExtra("imgUrl").toString();
            InvImgUrl = imgUrl;
            beerName.setText(intent.getCharSequenceExtra("beerName"));
            brewery.setText(intent.getCharSequenceExtra("brewery"));
            beerType.setText(intent.getCharSequenceExtra("beerType"));
        }

    }

    private void showAlert() {

        AlertDialog.Builder alertbox = new AlertDialog.Builder(this);

        alertbox.setMessage("Do you want to Add This beer to your Inventory?");

        alertbox.setPositiveButton("Add", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub Add Beer to Inventory
                String postUrl = "https://140.192.30.230:8443/beertrader/rest/offerable/addOfferable";
                new POST().execute(postUrl, beerName.getText().toString(), beerType.getText().toString(), brewery.getText().toString());
                Beer b = new Beer(beerName.getText().toString(), brewery.getText().toString(), beerType.getText().toString(), InvImgUrl);
                PersistentData.beerInventory.add(b);

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


    class POST extends AsyncTask<String, Void, Void> {
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
            httpPostReq.setHeader("Authorization", PersistentData.authorization);
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
