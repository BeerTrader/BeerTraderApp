package com.jim.demo1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

/**
 * Created by Jim on 2/2/2015.
 */
public class search_activity extends Activity {

    ImageButton button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        final AutoCompleteTextView beerType = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextViewType);
        final AutoCompleteTextView brewery = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextViewBrewery);


        //Once the next button is hit on the item keyboard hide the keyboard, jump to the set price function, then move focus to the quantity text field
        beerType.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        boolean handled = false;
                        if (actionId == EditorInfo.IME_ACTION_NEXT) {
                            InputMethodManager imm = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(beerType.getWindowToken(), 0);
                            handled = true;
                            brewery.requestFocus();
                        }
                        return handled;
                    }
                });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, BEERS);

        AutoCompleteTextView itemCurrent = (AutoCompleteTextView) findViewById(R.id.AutoCompleteTextViewType);

        itemCurrent.setAdapter(adapter);

    }

    static final String[] BEERS = new String[]{"Pilsner", "Stout", "Brown", "IPA", "Cider"};
}


