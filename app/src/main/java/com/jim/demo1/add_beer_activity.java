package com.jim.demo1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jim on 4/7/2015.
 */
public class add_beer_activity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_beer_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            TextView beerName = (TextView) findViewById(R.id.beerName);
            TextView brewery= (TextView) findViewById(R.id.brewery);
            TextView beerType = (TextView) findViewById(R.id.beerType);
            ImageView imgUrl = (ImageView) findViewById(R.id.lablePic);
//            String detailURL = intent.getCharSequenceExtra("detailURL").toString();
//            name.setText(intent.getCharSequenceExtra("name"));
//            company.setText(intent.getCharSequenceExtra("company"));
//            phone.setText(intent.getCharSequenceExtra("workNum"));
//            birthday.setText(intent.getCharSequenceExtra("birthdate"));
//            GetDetails task = new GetDetails(this.getApplicationContext(), detailURL);
//            task.execute();
        }
    }




}
