package com.jim.demo1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jim on 4/7/2015.
 */
public class add_beer_activity extends Activity{

    ImageButton addBeerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_beer_layout);
        addListenerOnButton();
    }

    private void addListenerOnButton() {

        addBeerButton = (ImageButton) findViewById(R.id.imageButton);

        addBeerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                showAlert();
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            TextView beerName = (TextView) findViewById(R.id.beerName);
            TextView brewery= (TextView) findViewById(R.id.brewery);
            TextView beerType = (TextView) findViewById(R.id.beerType);
            String imgUrl = intent.getCharSequenceExtra("imgUrl").toString();
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



}
