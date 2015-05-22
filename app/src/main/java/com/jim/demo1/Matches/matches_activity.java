package com.jim.demo1.Matches;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jim.demo1.AppSettings.app_settings_activity;
import com.jim.demo1.MainActivity;
import com.jim.demo1.Objects.Match;
import com.jim.demo1.R;

import java.util.ArrayList;

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
    }

    private void setAccpetBtn() {
        acceptBtn = (Button) findViewById(R.id.acceptBtn);
        acceptBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                matchText.setText("");
                matches.remove(0);
                if (!matches.isEmpty()) {
                    Match match = matches.get(0);
                    offerer = match.getOfferer().getUsername().toString();
                    desirer = match.getDesirer().getUsername().toString();
                    offerable = match.getOfferable().getName().toString();
                    desirable = match.getDesirable().getName().toString();
                    matchText.setText(offerer + " is offering " + offerable + " and " + desirer + " wants " + desirable);
                    System.out.println(matches.size());
                }
                else{
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
                    Intent intent = new Intent(context, app_settings_activity.class);
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


    private void setRejectBtn() {
        rejectBtn = (Button) findViewById(R.id.rejectBtn);
        rejectBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                matchText.setText("");
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


}