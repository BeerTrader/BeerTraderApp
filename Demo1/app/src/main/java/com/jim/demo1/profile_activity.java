package com.jim.demo1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;

/**
 * Created by Jim on 2/2/2015.
 */
public class profile_activity extends Activity {
    ImageButton button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
    }
}