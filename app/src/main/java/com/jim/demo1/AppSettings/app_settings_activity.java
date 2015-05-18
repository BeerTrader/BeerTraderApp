package com.jim.demo1.AppSettings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jim.demo1.Favorites.FavesPage;
import com.jim.demo1.Favorites.Favorites;
import com.jim.demo1.R;

/**
 * Created by Jim on 2/17/2015.
 */
public class app_settings_activity extends Activity{

    Button FavsButton;
    Button MyFavsButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_settings_layout);
        addToFavoritesButton();
        myFavoritesButton();


    }

    private void addToFavoritesButton() {
        FavsButton = (Button) findViewById(R.id.addToFavoritesButton);

        final Context context = this;

           FavsButton.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View arg0) {

                   Intent intent = new Intent(context, FavesPage.class);
                   startActivity(intent);

               }

           });

    }

    private void myFavoritesButton() {
        MyFavsButton = (Button) findViewById(R.id.myFavoritesButton);

        final Context context = this;

        MyFavsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Favorites.class);
                startActivity(intent);

            }

        });

    }

    }
