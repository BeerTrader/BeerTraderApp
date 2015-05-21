package com.jim.demo1.Matches;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.jim.demo1.Objects.Match;
import com.jim.demo1.R;

import java.util.ArrayList;

/**
 * Created by Jim on 2/2/2015.
 */
public class matches_activity extends Activity {

    ArrayList<Match> matches = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent != null) {
            TextView name = (TextView) findViewById(R.id.offerer);
            matches = intent.getParcelableArrayListExtra("matches");
            Match match = matches.get(0);
            name.setText(match.getOfferer().toString());
        }
    }


}